package com.example.spring_01.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spring_01.pojo.User;
import com.example.spring_01.pojo.UserI;
import com.example.spring_01.pojo.UserP;
import com.example.spring_01.pojo.Userc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService extends IService<User>{
/*
* 查询用户所有基本信息的方法声明
* */
    List<UserI> setUser(String id);
    /*
    * 修改用户信息的方法声明
    * */
    void update(User user);

    List<UserI> setUser1();

    void delete(String id);


    /*List<User> list();

    void delete(String id);

    void update(User user);

    void insert(User user);

    List<UserI> setUser(String id);

    List<Map<String, Object>> searchById(String id);*/

}
