package com.projeto.bankapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @Column(name = "numerodeconta", nullable = false)
    private Integer numerodeconta;


    @Column(nullable = false)
    private int titularprincipal;
    @Column(nullable = false)
    private double saldo;




}