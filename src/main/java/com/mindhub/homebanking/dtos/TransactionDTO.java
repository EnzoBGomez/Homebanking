package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

    public long getId() {
        return id;
    }


    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
