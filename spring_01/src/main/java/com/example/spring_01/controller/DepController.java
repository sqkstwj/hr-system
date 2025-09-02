package com.example.spring_01.controller;

import com.example.spring_01.mapper.DepMapper;
import com.example.spring_01.pojo.Dep;
import com.example.spring_01.pojo.PostT;
import com.example.spring_01.pojo.Result;
import com.example.spring_01.service.DepService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class DepController {
    @Autowired
    private DepService depService;
    @Autowired
    private DepMapper depMapper;


    @RequestMapping("/select1")
    public List<Dep>select1(){
        return depMapper.selectList(null);
    }























    /*@GetMapping(value = "/depts")//查询全部数据
    public Result list(){
        log.info("查询全部部门数据");
        List<Dep>deplist=depService.list();
        return Result.success(deplist);

    }

    @DeleteMapping("/depts/{id}")
    public Result delete(@PathVariable String id){
        log.info("根据id删除部门：{}",id);
        depService.delete(id);
        return Result.success();

    }

    @PostMapping("/depts")
    public Result add(@RequestBody Dep dep){
        log.info("新增部门：{}",dep);
        depService.add(dep);
        return Result.success();

    }

    @PutMapping("/depts")
    public Result update(@RequestBody Dep dep){
        log.info("更新部门：{}",dep);
        depService.update(dep);
        return Result.success();
    }*/





}
