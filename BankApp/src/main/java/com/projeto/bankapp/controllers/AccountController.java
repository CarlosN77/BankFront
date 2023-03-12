package com.projeto.bankapp.controllers;

import com.projeto.bankapp.entities.AccountEntity;
import com.projeto.bankapp.entities.ClientEntity;
import com.projeto.bankapp.repositories.AccountRepository;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Controller
public class AccountController {

    @Autowired
    private final AccountRepository accountRepository;


    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);



    @GetMapping("/accounts")
    public String getAccountsForClient(HttpSession session, Model model) throws AuthenticationException {
        ClientEntity cliente = (ClientEntity) session.getAttribute("cliente");
        if (cliente == null) {
            throw new AuthenticationException("User not logged in");
        }
        List<AccountEntity> accounts = accountRepository.findByTitularprincipal(cliente.getNif());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
        } else {
            for (AccountEntity account : accounts) {

            }
        }
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/createNewAccount")
    public String createaccount(HttpSession session) throws AuthenticationException, Exception {
        ClientEntity cliente = (ClientEntity) session.getAttribute("cliente");
        if (cliente == null) {
            throw new AuthenticationException("User not logged in");
        }

        // Check if the client already has three accounts with the same titularprincipal
        int numAccounts = accountRepository.countByTitularprincipal(cliente.getNif());
        if (numAccounts >= 3) {
            throw new Exception("Client already has three accounts");
        }

        // Generate a unique 4-digit account number
        Random random = new Random();
        int accountNumber;
        boolean exists;
        do {
            accountNumber = random.nextInt(9000) + 1000; // Generates a number between 1000 and 9999
            exists = accountRepository.findAllByNumerodeconta(accountNumber) != null;
        } while (!exists);

        // Create the new account entity and set its properties
        AccountEntity newAccount = new AccountEntity();
        newAccount.setNumerodeconta(accountNumber);
        newAccount.setSaldo(50);
        newAccount.setTitularprincipal(cliente.getNif());

        // Save the new account to the database
        logger.info("Saving new account to database...");
        accountRepository.save(newAccount);
        logger.info("New account saved to database.");

        return "redirect:/afterlogin"; // Redirect to the account created page
    }
    @Transactional
    @GetMapping("addSecondaryHolder")
    public String addSecondaryHolder(int accountNumber, int secondaryHolderNif) {
        // Find the account entity by its account number
        AccountEntity account = accountRepository.findOneByNumerodeconta(accountNumber);

        // If the account entity was found, add the secondary holder's NIF to the list of secondary holders
        if (account != null) {
            List<Integer> secondaryHolders = account.getTitularessecundarios();
            if (secondaryHolders == null) {
                secondaryHolders = new ArrayList<>();
            }
            secondaryHolders.add(secondaryHolderNif);
            account.setTitularessecundarios(secondaryHolders);

            // Update the account entity in the database
            accountRepository.save(account);
            logger.info("Secondary holder with NIF " + secondaryHolderNif + " added to account " + accountNumber);
        } else {
            logger.warn("Account " + accountNumber + " not found");
        }
        return "redirect:/afterlogin";
    }







}


