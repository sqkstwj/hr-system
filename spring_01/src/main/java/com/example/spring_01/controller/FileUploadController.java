package com.example.spring_01.controller;

import com.example.spring_01.pojo.Result;
import com.example.spring_01.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
public class FileUploadController {
    
    @Autowired
    private AliOssUtil aliOssUtil;
    
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception{
        String originalFilename=file.getOriginalFilename();
        String filename= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = aliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(url);
    }
}
