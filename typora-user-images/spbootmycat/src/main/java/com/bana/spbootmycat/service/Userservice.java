package com.bana.spbootmycat.service;

import com.bana.spbootmycat.dao.UserMapper;
import com.bana.spbootmycat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Userservice {
    @Resource
    private UserMapper userMapper;

    public void addUser(User user){

        userMapper.insert(user);
    }

    public void selectAll(){
        List<User> userList=userMapper.selectAll();
        for(User user:userList){
            System.out.println(user.getName());
        }
    }




}
