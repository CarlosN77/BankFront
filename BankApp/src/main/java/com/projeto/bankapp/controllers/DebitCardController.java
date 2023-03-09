package com.projeto.bankapp.controllers;

import com.projeto.bankapp.entities.AccountEntity;
import com.projeto.bankapp.entities.DebitCardEntity;
import com.projeto.bankapp.repositories.AccountRepository;
import com.projeto.bankapp.repositories.DebitCardRepository;
import com.projeto.bankapp.services.DebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/debit-cards")
public class DebitCardController {

    private final AccountRepository accountRepository;
    private final DebitCardRepository debitCardRepository;
    @Autowired
    private DebitCardService debitCardService;

    public DebitCardController(AccountRepository accountRepository, DebitCardRepository debitCardRepository, DebitCardService debitCardService) {
        this.accountRepository = accountRepository;
        this.debitCardRepository = debitCardRepository;
        this.debitCardService = debitCardService;
    }




    @PostMapping("/createdebit")
    public ModelAndView createDebitCard(@RequestParam("account") Long accountId,
                                  @RequestParam("pin") int pin,
                                  Model model) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));
        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        DebitCardEntity debitCard = new DebitCardEntity();
        debitCard.setNumerodecartao(randomNum);
        debitCard.setConta(account.getNumerodeconta());
        debitCard.setPin(pin);
        debitCardRepository.save(debitCard);
        model.addAttribute("debitCard", debitCard);
        return new ModelAndView("afterlogin");
    }


}