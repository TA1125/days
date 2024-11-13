package com.example.demo.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Memo {
    private Integer memoId;
    private Integer setId; // ユーザーID
    private String content; // メモの内容
    private Date memoDate;
    private Date daysDate;
}
