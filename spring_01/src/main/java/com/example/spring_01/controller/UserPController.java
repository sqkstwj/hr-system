package com.example.spring_01.controller;
import com.example.spring_01.pojo.UserP;
import com.example.spring_01.pojo.Result;
import com.example.spring_01.service.UserPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/userPts")
public class UserPController {
        @Autowired
        private UserPService userPService;

        @GetMapping(value = "/selectAll")//查询全部数据
        public Result list(){
            log.info("查询全部任岗数据");
            List<UserP> userPlist=userPService.list();
            return Result.success(userPlist);
        }

        //根据id删除任岗数据
        @DeleteMapping("/delete/{id}")
        public Result delete(@PathVariable String id){
            log.info("根据id删除任岗：{}",id);
            userPService.delete(id);
            return Result.success();

        }

        //新增任岗数据，在插入用户数据后使用
        @PostMapping("/add")
        public Result insert(@RequestBody UserP userP){
            log.info("新增任岗：{}",userP);
            userPService.insert(userP);
            return Result.success();

        }

        //员工任岗状态的禁用或解禁
        @PutMapping("/update")
        public Result update(@RequestBody UserP userP){
            log.info("更新任岗：{}",userP);
            userPService.update(userP);
            return Result.success();
        }

}
