package com.example.spring_01.controller;

import com.example.spring_01.mapper.AdsMapper;
import com.example.spring_01.pojo.Ads;
import com.example.spring_01.pojo.Pwd;
import com.example.spring_01.service.AdsService;
import com.example.spring_01.utils.JwtUtil;
import com.example.spring_01.utils.ThreadLocalUtil;
import io.netty.util.internal.StringUtil;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import com.example.spring_01.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RestController
@Slf4j
@Validated
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    private AdsService adsService;
    @Autowired
    private AdsMapper adsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@RequestParam @Pattern(regexp = "^\\S{5,18}$") String adsName,@RequestParam @Pattern(regexp = "^\\S{5,18}$") String password){
        Ads ads=adsService.findByAdsName(adsName);
        if(ads==null){
            adsService.register(adsName,password);
            return Result.success();
        }else {
            return Result.error("用户名已被占用");
        }

    }

    @PostMapping(value = "/login")
    public Result<String> login(@RequestParam @Pattern(regexp = "^\\S{5,18}$") String adsName, @RequestParam @Pattern(regexp = "^\\S{5,18}$") String password){

        Ads loginAds=adsService.findByAdsName(adsName);
        if(loginAds==null){
            return Result.error("用户名错误");
        }
        if(password.equals(loginAds.getPassword())){

            Map<String,Object> claims= new HashMap<>();
            claims.put("id",loginAds.getId());
            claims.put("adsName",loginAds.getAdsName());
            String token=JwtUtil.genToken(claims);

            //把token存到redis
            ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);


            return Result.success(token);
        }
        return Result.error("密码错误");

    }


//修改昵称
    @PutMapping("/update")
    public Result update(@RequestBody Ads ads){
        ads.setUpdateTime(LocalDateTime.now());
        adsMapper.update(ads);
        return Result.success();

    }
    //修改用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvater(@RequestParam String avatarUrl){
        adsService.updateAvater(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Pwd pwd){
        //校验参数
        /*String oldPwd=params.get("old_pwd");
        String newPwd=params.get("new_pmd");
        String rePwd=params.get("re_pwd");*/

        /*if(StringUtils.hasLength(oldPwd)|| !StringUtils.hasLength(newPwd)|| !StringUtils.hasLength(rePwd))
        {
            return Result.error("缺少对应参数");
        }*/
        String oldPwd=pwd.getOldPwd();
        String newPwd=pwd.getNewPwd();
        String rePwd=pwd.getRePwd();

        if(!StringUtils.hasLength(oldPwd)|| !StringUtils.hasLength(newPwd)|| !StringUtils.hasLength(rePwd))
        {
            return Result.error("缺少对应参数");
        }

        //检验原密码
        Map<String,Object>map=ThreadLocalUtil.get();
        String adsName=(String) map.get("adsName");
        Ads ads=adsService.findByAdsName(adsName);
        if(!(ads.getPassword().equals(oldPwd))){
            return Result.error("原密码不正确");
        }
        if(!(rePwd.equals(newPwd))){
            return Result.error("两次填写的密码不一致");
        }
        adsService.updatePwd(newPwd);
        return Result.success();


    }

    //获取用户基本信息
    @GetMapping("/adsInfo")
    public Result<Ads>adsInfo(@RequestHeader(name="Authorization") String token){
        Map<String,Object> map=JwtUtil.parseToken(token);
        String adsName=(String)map.get("adsName");
        Ads ads=adsService.findByAdsName(adsName);
        return Result.success(ads);
    }



}
