package com.example.spring_01.mapper;

import com.example.spring_01.pojo.Ads;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface AdsMapper {
    @Select("select * from ads where ads_name=#{adsName}")
    Ads findByAdsName(String adsName);

    @Insert("insert into ads(ads_name, password, id) values (#{adsName},#{password},UUID())")
    void add(String adsName, String password);

    @Update("update ads set nick_name=#{nickName},update_time=#{updateTime} where id=#{id}")
    void update(Ads ads);

    @Update("update ads set load_tx=#{avatarUrl}, update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,String id);

    @Update("update ads set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd, String id);
}
