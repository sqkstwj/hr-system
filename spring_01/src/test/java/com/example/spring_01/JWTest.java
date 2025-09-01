package com.example.spring_01;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTest {
    @Test
    public void testGen(){
        Map<String,Object> claims =new HashMap<>();
        claims.put("id",1);
        claims.put("adsName","tldwxz");
        //生成jwt
        String  token=JWT.create()
                .withClaim("ads",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+10000*60*60*12))
                .sign(Algorithm.HMAC256("sqks"));//配置密钥
        System.out.println(token);

    }

    @Test
    public void testParse(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhZHMiOnsiYWRzTmFtZSI6InRsZHd4eiIsImlkIjoxfSwiZXhwIjoxNzA2NjgwOTIzfQ.CuLi7NO83P1ZOq6oClKZclCXUGG77jDb2-kPDHM2fC0";
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("sqks")).build();
        DecodedJWT decodeJWT=jwtVerifier.verify(token);//验证token
        Map<String, Claim> claims=decodeJWT.getClaims();
        System.out.println(claims.get("ads"));


    }
}
