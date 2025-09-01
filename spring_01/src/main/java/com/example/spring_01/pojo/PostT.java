package com.example.spring_01.pojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("position")
public class PostT {
    String id;
    String shortname;
    String fullName;
    LocalDateTime createDate;
    String postcode;
    String deptId;
    String responsibility;
    String remark;
    String createBy;
    String status;
    Integer sort;

}
