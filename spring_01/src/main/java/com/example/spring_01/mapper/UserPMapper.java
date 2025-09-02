package com.example.spring_01.mapper;

import com.example.spring_01.pojo.UserP;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserPMapper {
    //@Select("select * from userpost")
    List<UserP> list();

    @Delete("delete from userpost where id=#{id}")
    void deleteById(String id);

    //@Insert("insert into userpost(userId,postId,createTime) values (#{userId},#{postId},#{createTime})")
    void insert(@Param("userP") UserP userP);

    //@Update("update `userpost` set userId=#{userId},postId=#{postId},updateTime=#{updateTime} where id=#{id}")
    void update(UserP userP);
}
