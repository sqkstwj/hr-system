package com.example.spring_01;

import com.example.spring_01.mapper.DepMapper;
import com.example.spring_01.pojo.Dep;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class Spring01ApplicationTests {
    @Autowired
    private DepMapper depMapper;

    @Test
    void contextLoads() {
        List<Dep>depList=depMapper.selectList(null);
        depList.forEach(System.out::println);

    }

}
