package com.projeto.bankapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AfterLoginController {

    @GetMapping("/createdebit")
    public String debitcardcreation(){
        return "createdebit.html";
    }


    @GetMapping("/accountspage")
    public String vercontas(){
        return "accounts.html";
    }

    @GetMapping("/index")
    public String index(){
        return "index.html";
    }

    @GetMapping("/createaccount")
    public String createNewAccount(){
        return "createaccount.html";
    }
    @GetMapping("/createsectitular")
    public String createsectitulart(){
        return "createsectitular.html";
    }


}
