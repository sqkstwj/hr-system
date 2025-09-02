package com.example.spring_01.mapper;

import com.example.spring_01.pojo.Ads;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface AdsMapper {
    @Select("select * from ads where adsName=#{adsName}")
    Ads findByAdsName(String adsName);

    @Insert("insert into ads(adsName, password, id) values (#{adsName},#{password},UUID())")
    void add(String adsName, String password);

    @Update("update ads set nickName=#{nickName},updateTime=#{updateTime} where id=#{id}")
    void update(Ads ads);

    @Update("update ads set loadTx=#{avatarUrl}, updateTime=now() where id=#{id}")
    void updateAvatar(String avatarUrl,String id);

    @Update("update ads set password=#{newPwd},updateTime=now() where id=#{id}")
    void updatePwd(String newPwd, String id);
}
