package com.example.spring_01.service.impl;

import com.example.spring_01.mapper.DepMapper;
import com.example.spring_01.pojo.Dep;
import com.example.spring_01.service.DepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepServicelmpl implements DepService {
    @Autowired
    private DepMapper depMapper;

















    /*@Override
    public List<Dep> list(){
        return depMapper.list();
    }

    @Override
    public void delete(String id) {
        depMapper.deleteById(id);
    }

    @Override
    public void add(Dep dep) {
        dep.setCreate_date(LocalDateTime.now());
        depMapper.insert(dep);
    }

    @Override
    public void update(Dep dep) {
        dep.setUpdate_date(LocalDateTime.now());
        depMapper.update(dep);

    }*/


}
