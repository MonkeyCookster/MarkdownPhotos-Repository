package com.bana.spbootmycat.dao;

import com.bana.spbootmycat.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    List<User> selectAll();
}