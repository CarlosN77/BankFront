package com.projeto.bankapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AfterLoginController {

    @GetMapping("/a")
    public String debitcardcreation(){
        return "a.html";
    }
}
