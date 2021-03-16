package com.spring.smartcontact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("smart","this is smart contact manager");

        return "home";
    }
    @RequestMapping("/base")
    public String base(){

        return "base";
    }
    @RequestMapping("/signUp")
    public String signUp(){

        return "signUp";
    }
}
