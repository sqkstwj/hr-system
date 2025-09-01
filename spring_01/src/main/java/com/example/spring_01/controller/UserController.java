package com.example.spring_01.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.spring_01.mapper.UserMapper;
import com.example.spring_01.pojo.*;
import com.example.spring_01.service.UserPService;
import com.example.spring_01.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.UUID.randomUUID;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /*
    * 查询全部用户表数据*/
    @GetMapping("/allUser")
    public List<User> findAllUser(){
        return userService.list();
    }


    /*
    * 用户的信息注册，将用户基本信息插入用户表
    *
    * */
    @PostMapping("/addUser")
    public Result add(@RequestBody User user){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("emp_no")
                .orderByDesc("emp_no")
                .last("LIMIT 1");

        User user1 = userMapper.selectOne(wrapper);
        String str=UUID.randomUUID().toString();
        user.setId(str.replaceAll("-", ""));
        user.setCreate_date(LocalDateTime.now());
        user.setUpdate_date(LocalDateTime.now());
        user.setEntrydate(LocalDateTime.now());
        user.setEmpNo(String.valueOf(Integer.parseInt(user1.getEmpNo())+1));
        return Result.success(userService.save(user));
    }

    /*
    * 通过id查询指定用户的全部信息
    * */
    @GetMapping( "/selects/{id}")//查询用户数据
    public Result setUser(@PathVariable String id){
        log.info("查询全部用户数据");
        List<UserI> userIlist=userService.setUser(id);

        return Result.success(userIlist);

    }
    @GetMapping( "/userInfo")//查询用户数据
    public Result setUser1(){
        log.info("查询全部用户数据");
        List<UserI> userIlist=userService.setUser1();
        return Result.success(userIlist);

    }


    /*
    * 用户表的更新，用以离职或复职
    * */
    @PutMapping("/users")
    public Result update(@RequestBody User user){
        log.info("更新用户：{}",user);
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        log.info("根据id删除任岗：{}",id);
        userService.delete(id);
        return Result.success();

    }






















    /*@GetMapping("/selects")
    public List<Map<String,Object>> Search(HttpServletRequest request ){
        String id=request.getParameter( "id" );
        List< Map< String, Object > > list = userService.searchById( id );
        return list;
    }


    @GetMapping( "/users")//查询全部数据
    public Result list(){
        log.info("查询全部用户数据");
        List<User> userlist=userService.list();
        return Result.success(userlist);

    }

    @RequestMapping(value = "/users/{id}")//查询用户数据
    public Result setUser(@PathVariable String id){
        log.info("查询全部用户数据");
        List<UserI> userIlist=userService.setUser(id);
        return Result.success(userIlist);

    }



    @DeleteMapping("/users/{id}")
    public Result delete(@PathVariable String id){
        log.info("根据id删除用户：{}",id);
        userService.delete(id);
        return Result.success();

    }

    @PostMapping("/users")
    public Result insert(@RequestBody User user){
        log.info("新增用户：{}",user);
        userService.insert(user);
        return Result.success();

    }
    @PutMapping("/users")
    public Result update(@RequestBody User user){
        log.info("更新用户：{}",user);
        userService.update(user);
        return Result.success();
    }
*/
}
