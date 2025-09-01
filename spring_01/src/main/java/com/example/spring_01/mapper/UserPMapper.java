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

    //@Insert("insert into userpost(userid,postid,createtime) values (#{userid},#{postid},#{createtime})")
    void insert(@Param("userP") UserP userP);

    //@Update("update `userpost` set userid=#{userid},postid=#{postid},updatetime=#{updatetime} where id=#{id}")
    void update(UserP userP);
}
