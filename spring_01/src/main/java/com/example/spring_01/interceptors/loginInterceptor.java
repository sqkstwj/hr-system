package com.example.spring_01.interceptors;

import com.example.spring_01.utils.JwtUtil;
import com.example.spring_01.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class loginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handle)throws Exception{
        //令牌验证
        String token=request.getHeader("Authorization");
        try {

            //从redis获取相通的token
            ValueOperations<String,String> operations= stringRedisTemplate.opsForValue();
            String redisToken=operations.get(token);
            if(redisToken==null){
                //失效
                throw  new RuntimeException();
            }
            Map<String,Object> claims= JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

    }
    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handle,Exception ex) throws  Exception{
        ThreadLocalUtil.remove();
    }

}
