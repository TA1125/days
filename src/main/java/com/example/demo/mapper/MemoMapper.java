package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Memo;

@Mapper
public interface MemoMapper {
    void insertMemo(Memo memo);
    
    List<Memo> getMemosByUserId(Integer userId);
    
}
