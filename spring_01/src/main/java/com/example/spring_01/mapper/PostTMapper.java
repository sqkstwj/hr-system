package com.example.spring_01.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_01.pojo.PostT;
import com.example.spring_01.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostTMapper extends BaseMapper<PostT> {
    @Select("select * from position")
    List<PostT> list();

    @Delete("delete from position where id=#{id}")
    void deleteById(String id);

    /*@Insert("insert into `position`(shortname,fullname,createdate) values (#{shortname},#{fullname},#{createdate})")
    void insert(PostT postT);*/

    @Update("update `position` set shortname=#{shortname},fullName=#{fullName},createDate=#{createDate} where id=#{id}")
    void update(PostT postT);
}
