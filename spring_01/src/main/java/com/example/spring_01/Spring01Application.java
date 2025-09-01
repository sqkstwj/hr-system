package com.example.spring_01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.spring_01.mapper")
@SpringBootApplication

public class Spring01Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring01Application.class, args);
    }

}
