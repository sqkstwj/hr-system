package com.example.spring_01.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring_01.pojo.Dep;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
@Mapper
@Repository
public interface DepMapper extends BaseMapper<Dep> {



    //@Select("select * from t_s_depart")
    List<Dep> list();


    /*@Delete("delete from t_s_depart where id=#{id}")
    void deleteById(String id);

    @Insert("insert into t_s_depart(departname,description,parentdepartid,create_date) values (#{departname},#{description},#{parentdepartid},#{create_date})")
    void insert(Dep dep);


    @Update("update `t_s_depart` set departname=#{departname},description=#{description},parentdepartid=#{parentdepartid},update_date=#{update_date} where id=#{id}")
    void update(Dep dep);*/
}
