package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Memo;
import com.example.demo.model.User;

@Service
public class MemoService {

    @Autowired
    private MemoMapper memoMapper;

    @Autowired
    private UserMapper userMapper;

    // ユーザー名からユーザーIDを取得
    public Integer getUserIdByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            System.out.println("No user found for username: " + username);  // ユーザーが見つからない場合
        } else {
            System.out.println("Found user: " + user.getUserName() + ", ID: " + user.getUserId());  // ユーザーが見つかった場合
        }
        return user != null ? user.getUserId() : null;  // ユーザーIDを返す
    }

    // メモをデータベースに保存
    public void saveMemo(Memo memo) {
        memoMapper.insertMemo(memo);  // Memoを保存
    }

    // ユーザーIDに関連するすべてのメモを取得
    public List<Memo> getMemosByUserId(Integer userId) {
        List<Memo> memos = memoMapper.getMemosByUserId(userId);
        System.out.println("Found memos for userId " + userId + ": " + memos);  // メモのデバッグ
        return memos;
    }
}
