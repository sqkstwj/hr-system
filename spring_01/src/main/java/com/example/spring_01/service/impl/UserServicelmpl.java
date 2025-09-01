package com.example.spring_01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_01.mapper.UserMapper;
import com.example.spring_01.pojo.User;
import com.example.spring_01.pojo.UserI;
import com.example.spring_01.pojo.Userc;
import com.example.spring_01.service.UserService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServicelmpl  extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;


    //根据id查询用户及有关数据
    @Override
    public List<UserI> setUser(String id) {
        List<UserI> userIList=userMapper.setUser(null);
        return userMapper.setUser(id);
    }


    //更新用户数据，离职或在职
    @Override
    public void update(User user) {
        user.setUpdate_date(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public List<UserI> setUser1() {
        return userMapper.setUser1();
    }

    @Override
    public void delete(String id) {
        userMapper.deleteById(id);
    }





    /*@Resource(name = "jdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> searchById(String id){
        String sql1="select * from `user` where id=?";
        List< Map< String, Object > > list = jdbcTemplate.queryForList( sql1, id );
        return  list;
    }


    //查询所有用户数据
    @Override
    public List<User> list(){
        return userMapper.list();
    }

    //根据id删除用户表中的数据
    @Override
    public void delete(String id) {
        userMapper.deleteById(id);
    }

    //插入用户数据
    @Override
    public void insert(User user) {
        user.setCreate_date(LocalDateTime.now());
        user.setEntryDate(LocalDateTime.now());
        userMapper.insert(user);
    }

    //根据id查询用户及有关数据
    @Override
    public List<UserI> setUser(String id) {
        List<UserI> userIList=userMapper.setUser(null);
        return userMapper.setUser(id);
    }


    //更新用户数据，离职或在职
    @Override
    public void update(User user) {
        user.setUpdate_date(LocalDateTime.now());
        userMapper.update(user);
    }
*/
}
