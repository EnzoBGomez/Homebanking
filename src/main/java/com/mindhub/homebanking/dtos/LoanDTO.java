package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.Models.LoanType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private Float interest;


    public LoanDTO() {
    }
    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.maxAmount = loan.getMaxAmount();
        this.name = loan.getName();
        this.payments = loan.getPayments();
        this.interest = loan.getInterest();
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Float getInterest() {
        return interest;
    }
}
