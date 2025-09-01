package com.example.spring_01.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_s_depart")
public class Dep {
    String id;
    String departname;
    String description;
    String parentdepartid;
    String org_code;
    String org_type;
    String mobile;
    Integer depart_order;
    String memo;
    String create_name;
    String create_by;
    LocalDateTime create_date;
    String update_name;
    String update_by;
    LocalDateTime update_date;
    String status;

}
