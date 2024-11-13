 package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Memo;
import com.example.demo.service.MemoService;
@Controller
public class MemoController {

    @Autowired
    private MemoService memoService;

    @GetMapping("/memo/{date}")
    public String showMemoPage(@PathVariable("date") String date, Model model) {
        model.addAttribute("noteDate", date);  // メモの日付をビューに渡す
        return "memo";  // memo.htmlを表示
    }

    @PostMapping("/memo/save")
    public String saveMemo(@RequestParam("date") String date, @RequestParam("content") String content) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Authenticated username: " + username);  // ユーザー名の確認
        
        // ユーザーIDを取得
        Integer userId = memoService.getUserIdByUsername(username);
        System.out.println("User ID: " + userId); 
        // ユーザーIDがnullの場合はエラー処理
        if (userId == null) {
            return "error";  // ユーザーが見つからなかった場合はエラーページに遷移
        }

        // Memoインスタンスを作成
        Memo memo = new Memo();
        memo.setSetId(userId);  // ユーザーIDをセット
        memo.setContent(content);  // メモ内容をセット
        
        // String型の日付をjava.sql.Dateに変換
        Date sqlDate = Date.valueOf(date);  // String型の日付をDate型に変換
        memo.setMemoDate(sqlDate);  // メモ日付をセット
        memo.setDaysDate(sqlDate);  // メモの日付（カレンダーの日付）をセット
        System.out.println("Memo setId: " + memo.getSetId());  // setIdが正しくセットされているか確認
        // メモを保存
        memoService.saveMemo(memo);  // Memo型のみを渡す
        System.out.println("Memo saved successfully with content: " + memo.getContent());  // メモ保存後の確認
        
        return "redirect:/calendar";  // 保存後、カレンダーにリダイレクト
    }
    
    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        // ユーザーのメモ情報を取得
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = memoService.getUserIdByUsername(username);

        // ユーザーIDに関連するすべてのメモを取得
        List<Memo> memos = memoService.getMemosByUserId(userId);

        // メモ情報をモデルに追加
        model.addAttribute("memos", memos);

        return "calendar";  // カレンダーのビューに遷移
    }
}