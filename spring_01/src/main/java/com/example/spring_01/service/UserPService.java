package com.example.spring_01.service;
import com.example.spring_01.pojo.UserP;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserPService {
    /*
    * 查询任岗信息的方法声明
    * */
    List<UserP> list();
/*
* 根据id删除用户
* */
    void delete(String id);
/*
* 插入用户信息的方法声明
* */
    void insert(UserP userP);
/*
* 更改用户信息的方法声明
* */
    void update(UserP userP);
}
