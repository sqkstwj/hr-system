package com.example.spring_01.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("userpost")
public class UserP {
    @TableId(type = IdType.ASSIGN_UUID)
    String id;
    String userId;
    String postId;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    String status;
    String createBy;
    String updateBy;
    String userPostcode;


}
