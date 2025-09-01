package com.example.spring_01.service;
import com.example.spring_01.pojo.PostT;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostTService {
    /*
    * 查询岗位表的全部信息
    * */
    List<PostT> list();

    /*
    * 根据id删除岗位
    * */
    void delete(String id);
/*
* 插入新岗位
* */
    void add(PostT post);

    /*
    * 更新岗位的方法声明
    * */
    void update(PostT postT);
}
