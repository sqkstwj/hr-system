package com.example.spring_01.service.impl;

import com.example.spring_01.mapper.AdsMapper;
import com.example.spring_01.pojo.Ads;
import com.example.spring_01.service.AdsService;
import com.example.spring_01.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Service
public class AdsServicelmpl implements AdsService {
    @Autowired
    private AdsMapper adsMapper;
    @Override
    public Ads findByAdsName(String adsName) {
        Ads ads=adsMapper.findByAdsName(adsName);
        return ads;
    }

    @Override
    public void register(String adsName, String password) {
        adsMapper.add(adsName,password);

    }

    @Override
    public void updateAvater(String avatarUrl) {
        Map<String,Object>map= ThreadLocalUtil.get();
        String id=(String) map.get("id");
        System.out.println(id);
        adsMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object>map= ThreadLocalUtil.get();
        String id=(String) map.get("id");
        adsMapper.updatePwd(newPwd,id);
    }
}
