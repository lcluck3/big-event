package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
       User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //密码加密 使用MD5加密算法
        String md5String = Md5Util.getMD5String(password);

        //添加
        userMapper.add(username,md5String);
    }

    @Override
    public void updata(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updata(user);
    }

    @Override
    public void updataAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updataAvatar(avatarUrl,id);
    }

    @Override
    public void updataPwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updataPwd(Md5Util.getMD5String(newPwd),id);
    }
}
