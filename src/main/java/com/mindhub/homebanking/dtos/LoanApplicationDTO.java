package com.mindhub.homebanking.dtos;


public class LoanApplicationDTO {
    private Long idLoan;
    private Double amount;
    private Integer payments;
    private String accountNumber;

    public LoanApplicationDTO() {
    }


    public Long getIdLoan() {
        return idLoan;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setIdLoan(Long idLoan) {
        this.idLoan = idLoan;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
