package com.example.spring_01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userc {
    String email;
    String sex;
    String empStatus;
    String empNo;
}
