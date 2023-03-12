package com.projeto.bankapp.controllers;

import com.projeto.bankapp.entities.AccountEntity;
import com.projeto.bankapp.entities.ClientEntity;
import com.projeto.bankapp.entities.DebitCardEntity;
import com.projeto.bankapp.repositories.AccountRepository;
import com.projeto.bankapp.repositories.DebitCardRepository;
import com.projeto.bankapp.services.DebitCardService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
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
    public String createDebitCard(@RequestParam("accountNumber") long accountNumber,
                                  @ModelAttribute("debitCardForm") DebitCardForm debitCardForm,
                                  HttpSession session, Model model) {
        AccountEntity account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account number"));

        ClientEntity cliente = (ClientEntity) session.getAttribute("cliente");
        if (cliente != null) {
            // Check if the account has the logged-in client as titular principal
            if (account.getTitularprincipal() != cliente.getNif()) {
                throw new IllegalArgumentException("The account does not belong to the logged-in client");
            }
        }

        // Check if there is already a debit card associated with the account
        DebitCardEntity existingDebitCard = debitCardRepository.findByConta(accountNumber);
        if (existingDebitCard != null) {
            // A debit card for this account already exists, return an error view
            model.addAttribute("errorMsg", "A debit card for this account already exists");
            return "a";
        }

        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        DebitCardEntity debitCard = new DebitCardEntity();
        debitCard.setNumerodecartao(randomNum);
        debitCard.setConta(account.getNumerodeconta());
        debitCard.setPin(debitCardForm.getPin());
        debitCardRepository.save(debitCard);

        if (cliente != null) {
            // Get the accounts of the logged-in client
            List<AccountEntity> accounts = accountRepository.findByTitularprincipal(cliente.getNif());
            model.addAttribute("accounts", accounts);
            int nif = cliente.getNif();
            model.addAttribute("nif", nif);
        }

        return "redirect:/afterlogin";
    }






    @Getter
    @Setter
    public static class DebitCardForm {
        private int pin;
    }



}