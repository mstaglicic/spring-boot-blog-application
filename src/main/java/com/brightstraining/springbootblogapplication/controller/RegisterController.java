package com.brightstraining.springbootblogapplication.controller;

import com.brightstraining.springbootblogapplication.model.UserAccount;
import com.brightstraining.springbootblogapplication.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RegisterController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/posts/register")
    public String getRegisterPage(Model model) {
        UserAccount userAccount = new UserAccount();
        model.addAttribute("userAccount", userAccount);
        return "registerForm";
    }

    @PostMapping("/posts/register")
    public String registerNewUser(@ModelAttribute UserAccount userAccount) {
        userAccountService.saveUser(userAccount);
        return "redirect:/posts";  //return to homepage after registering
    }

}
