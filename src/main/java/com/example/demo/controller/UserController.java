package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //注册用户
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        Result result = new Result();
        //查询用户
       User u = userService.findByUserName(username);
            //判断
            if (u == null) {
                //注册
                userService.register(username,password);
                return Result.success();
            }else {
                return   Result.error("username already exist");
            }
    }
    //登陆
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        Result result = new Result();
        //查询用户
        User u = userService.findByUserName(username);

        //是否查询到
       if (u == null) {
           return  Result.error("user is not exist");
       }
        //判断密码是否正确
       if (Md5Util.getMD5String(password).equals(u.getPassword())) {
           //登陆成功
           Map<String,Object> claims = new HashMap<>();
           claims.put("id",u.getId());
           claims.put("username",u.getUsername());
           String token = JwtUtil.genToken(claims);
           //把token传输到redis里面
           ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
           operations.set(token,token,12, TimeUnit.HOURS);
           return Result.success(token);
       }else {
           //登陆失败
           return  Result.error("password does not match");
       }

    }
    //查询用户数据
    @GetMapping("/userInfo")
    public  Result<User>  userInfo(/*@RequestHeader(name="Authorization") String token*/) {
        //解析token从而获取id和name
         /*Map<String,Object>  map= JwtUtil.parseToken(token);
         String username = (String) map.get("username");*/
        Map<String,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);

        return Result.success(user);
    }

    //更新用户数据
    @PutMapping("/updata")
    public Result update(@RequestBody @Validated User user) {
        userService.updata(user);
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updataAvatar(@RequestParam @URL String avatarUrl) {
        userService.updataAvatar(avatarUrl);
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token) {
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)) {
            return Result.error("you must specify old_pwd,new_pwd,re_pwd");
        }
        //原密码是否正确
        //先调用userService根据用户名拿到原密码,和old_pwd比较
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User u = userService.findByUserName(username);
        if (!u.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return  Result.error("password does not match");
        }
        if (!rePwd.equals(newPwd)){
            return  Result.error("two passwords do not match");
        }
        //调用service完成密码更新
        userService.updataPwd(newPwd);
        //删除redis对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
