package com.example.spring_01.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_01.pojo.User;
import com.example.spring_01.pojo.UserI;
import com.example.spring_01.pojo.Userc;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    List<UserI> setUser(String id);
    void update(@Param("user") User user);

    /*@Select("select email,sex,empStatus,empNo from user")*/
    List<UserI> setUser1();


    /*//@Select("select * from user")
    List<User> list();

    List<UserI> setUser(@Param("id") String id);

    @Delete("delete from user where id=#{id}")
    void deleteById(String id);

    //@Insert("insert into user(email,mobilePhone,create_date) values (#{email},#{mobilePhone},#{create_date})")

    //@Update("update `user` set email=#{email},mobilePhone=#{mobilePhone},update_date=#{update_date} where id=#{id}")
    void update(User user);*/

}
