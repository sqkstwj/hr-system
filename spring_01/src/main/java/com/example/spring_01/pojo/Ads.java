package com.example.spring_01.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ads")
public class Ads {
    String adsName;
    String id;
    String password;
    String loadTx;
    LocalDateTime updateTime;
    String nickName;

}
