package com.example.spring_01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spring_01.mapper.PostTMapper;
import com.example.spring_01.mapper.UserMapper;
import com.example.spring_01.pojo.PostT;
import com.example.spring_01.pojo.User;
import com.example.spring_01.service.PostTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostTServicelmpl extends ServiceImpl<PostTMapper, PostT> implements PostTService {
    @Autowired
    private PostTMapper postTMapper;
    @Override
    public List<PostT> list(){
        return postTMapper.list();
    }

    @Override
    public void delete(String id) {
        postTMapper.deleteById(id);
    }

    @Override
    public void add(PostT postT) {
        postT.setCreateDate(LocalDateTime.now());
        postTMapper.insert(postT);
    }

    @Override
    public void update(PostT postT) {
        postT.setCreateDate(LocalDateTime.now());
        postTMapper.update(postT);
    }
}
