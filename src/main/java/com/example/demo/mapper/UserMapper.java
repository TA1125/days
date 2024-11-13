package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	
    User findByUsername(String username);
    
    void insertUser(User user);
    
  
}

