package com.brightstraining.springbootblogapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/posts/login")
    public String getLoginPage() {
        return "loginForm";
    }

}
