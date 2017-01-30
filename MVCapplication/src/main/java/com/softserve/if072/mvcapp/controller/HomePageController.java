package com.softserve.if072.mvcapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping({"/", "/home"})
    public String getHomePage(){
//        model.addAttribute("title", "MVC application title");
//        model.addAttribute("body", "MVC application body");

        return "home";
    }
}
