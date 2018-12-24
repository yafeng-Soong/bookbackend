package com.example.bookbackend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/reset")
    public String reset(@RequestParam("email") String email, Model model){
        model.addAttribute("email",email);
        return "resetPwd";
    }
}
