package com.example.spring_01.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.ASSIGN_UUID)
    String id;
    String email;
    String mobilePhone;
    LocalDateTime create_date;
    LocalDateTime update_date;
    String update_name;
    String update_by;
    //@NotNull(message = "创建人不能为空")
    String create_name;
    String create_by;
    String person_type;
    String sex;
    String empNo;
    String leftreason;
    String workplace;
    LocalDateTime entrydate;
    String empStatus;
    String bossname;
    String bossid;



}
