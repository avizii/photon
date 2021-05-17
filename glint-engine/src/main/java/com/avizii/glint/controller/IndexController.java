package com.avizii.glint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
}
