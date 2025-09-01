package com.example.spring_01.controller;

import com.example.spring_01.pojo.Result;
import com.example.spring_01.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result<String> list(/*@RequestHeader(name="Authorization") String token, HttpServletResponse response*/){
        //验证tooken
        /*try {
            Map<String,Object>claims= JwtUtil.parseToken(token);
            return Result.success("所有数据");
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
        }*/

        return Result.success("所有数据");
    }

}
