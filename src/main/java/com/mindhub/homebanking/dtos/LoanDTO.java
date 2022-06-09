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
    private LoanType name;
    private Double maxAmount;
    private List<Integer> payments = new ArrayList<>();


    public LoanDTO() {
    }
    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.maxAmount = loan.getMaxAmount();
        this.name = loan.getName();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }


    public LoanType getName() {
        return name;
    }

    public void setName(LoanType name) {
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
}
