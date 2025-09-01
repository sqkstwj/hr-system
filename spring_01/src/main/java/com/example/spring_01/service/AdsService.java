package com.example.spring_01.service;

import com.example.spring_01.pojo.Ads;
import org.springframework.stereotype.Service;

@Service
public interface AdsService {

    Ads findByAdsName(String adsName);

    void register(String adsName, String password);

    void updateAvater(String avatarUrl);

    void updatePwd(String newPwd);
}
