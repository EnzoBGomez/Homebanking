package com.mindhub.homebanking.dtos;

import java.util.List;

public class NewLoanDTO {
    private Double maxAmount;
    private String typeLoan;
    private List<Integer> payments;
    private Float interest;


    public NewLoanDTO() {
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getTypeLoan() {
        return typeLoan;
    }

    public void setTypeLoan(String typeLoan) {
        this.typeLoan = typeLoan;
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

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public boolean existeNumeroNegativoPayments() {
        return this.payments.stream().anyMatch(i -> i<0);
    }
}
