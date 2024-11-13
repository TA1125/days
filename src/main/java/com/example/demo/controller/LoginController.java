package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserDetailsServiceImpl;

@Controller
public class LoginController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    // ログインページの表示
    @GetMapping("/login")
    public String login(String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "ユーザー名またはパスワードが間違っています。");
        }
        return "login";  // login.htmlを返す
    }

    // メニュー画面の表示
    @GetMapping("/menu")
    public String showMenu() {
        return "menu";  // menu.htmlを表示
    }

    // ユーザー登録フォームの表示
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // これにより、src/main/resources/templates/register.htmlが表示される
    }
    @GetMapping("/memo-calendar")
    public String showCalender() {
        return "calendar"; // calender.htmlへ
    }

    // ユーザー登録処理
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userDetailsServiceImpl.registerUser(user); // サービスクラスでユーザー登録処理
            model.addAttribute("message", "ユーザー登録が完了しました");
            return "registerSuccess";  // ユーザー登録完了後の画面に遷移
        } catch (Exception e) {
            model.addAttribute("errorMessage", "ユーザー登録に失敗しました。再度お試しください。");
            return "register"; // エラーメッセージを表示し、登録フォームに戻る
        }
    }
}

