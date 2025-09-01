package com.example.spring_01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.spring_01.mapper.PostTMapper;
import com.example.spring_01.mapper.UserMapper;
import com.example.spring_01.mapper.UserPMapper;
import com.example.spring_01.pojo.PostT;
import com.example.spring_01.pojo.User;
import com.example.spring_01.pojo.UserP;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.spring_01.service.UserPService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPServicelmpl implements UserPService {
    @Autowired
    private UserPMapper userPMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostTMapper postTMapper;

    //员工入职，任岗数据的导入
    @Override
    public List<UserP> list(){
        return userPMapper.list();
    }

    //删除任岗数据
    @Override
    public void delete(String id) {
        userPMapper.deleteById(id);
    }

    /*
    * 员工入职，新增任岗数据
    * */
    @Override
    public void insert(UserP userP) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("emp_no")
                    .orderByDesc("emp_no")
                .last("LIMIT 1");
        User user = userMapper.selectOne(wrapper);

        QueryWrapper<User> wrapper1=new QueryWrapper<>();
        wrapper1.eq("emp_no",user.getEmpNo());
        User user1=userMapper.selectOne(wrapper1);

        QueryWrapper<PostT> wrapper2=new QueryWrapper<>();
        wrapper2.eq("id",userP.getPostId());
        PostT postT=postTMapper.selectOne(wrapper2);


        //userP.setPostId(postId);
        userP.setUserId(user1.getId());//导入用户id
        userP.setCreateTime(LocalDateTime.now());
        userP.setUserPostcode(postT.getPostcode()+'_'+user.getEmpNo());//将拼接好的任岗编码引入实体类
        userPMapper.insert(userP);
    }

    /*
    * 员工的离职登记或是复职，通过id修改任岗数据
    *
    * */
    @Override
    public void update(UserP userP) {
        userP.setUpdateTime(LocalDateTime.now());
        userPMapper.update(userP);
    }
    
}
