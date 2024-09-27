package com.example.demo.interceptors;

import com.example.demo.pojo.Result;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptoer implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest requst, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
       String token =  requst.getHeader("Authorization");
        //验证token
            try {
                //从redis中获取相同的token
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                String redisToken = operations.get(token);
                if (redisToken == null) {
                    //token失效
                    throw new RuntimeException();
                }
                //把业务数据传输进ThreadLocal里
                Map<String,Object> claims =  JwtUtil.parseToken(token);
                ThreadLocalUtil.set(claims);
                //放行
                return true;
            } catch (Exception e) {
                //http响应状态码401
                response.setStatus(401);
                //拦截
                return false;
            }
        }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal数据
        ThreadLocalUtil.remove();
    }
}


