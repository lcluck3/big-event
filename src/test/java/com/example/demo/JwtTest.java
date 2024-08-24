package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id","1");
        claims.put("username","test");
        //生成jwt代码
        String token =  JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("test"));//指定算法

        System.out.println(token);
    }
    @Test
    public void testParse(){
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoidGVzdCJ9LCJleHAiOjE3MTc2MTE5Nzl9." +
                "DtV1HcVYVUccBT4UoQ8kCjclxskiwUDcKXZcmlr_bNY";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("test")).build();
        DecodedJWT jwt = jwtVerifier.verify(token);//验证token,生成一个解释后的JWT对象
        Map<String, Claim> claims = jwt.getClaims();
        System.out.println(claims);
        System.out.println(claims.get("user"));

    }
}
