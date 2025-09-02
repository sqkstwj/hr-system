package com.example.spring_01.controller;

import com.example.spring_01.pojo.PostT;
import com.example.spring_01.pojo.Result;
import com.example.spring_01.service.PostTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/post")
public class PostTContronller  {
    @Autowired
    private PostTService postTService;
    /*
    * 查询全部的岗位数据
    * */
    @GetMapping(value = "/posts")//查询全部数据
    public Result list(){
        log.info("查询全部岗位数据");
        List<PostT> postlist=postTService.list();
        return Result.success(postlist);

    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        log.info("查询全部岗位数据");
        List<PostT> postList = postTService.list();
        return Result.success(postList);
    }
/*
* 根据id删除岗位
* */
    @DeleteMapping("/posts/{id}")
    public Result delete(@PathVariable String id){
        log.info("根据id删除岗位：{}",id);
        postTService.delete(id);
        return Result.success();

    }
/*
* 新增岗位的信息插入
* */
    @PostMapping("/posts")
    public Result add(@RequestBody PostT postT){
        log.info("新增岗位：{}",postT);
        postTService.add(postT);
        return Result.success();

    }
/*
* 更新岗位的方法声明
* */
    @PutMapping("/posts")
    public Result update(@RequestBody PostT postT){
        log.info("更新岗位：{}",postT);
        postTService.update(postT);
        return Result.success();
    }



}
