package com.mindhub.homebanking.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LoanType name;
    private double maxAmount;
    @ElementCollection
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();


    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans;

    public Loan() {
    }

    public Loan(LoanType name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }


    public LoanType getName() {
        return name;
    }

    public void setName(LoanType name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
    //public List<Client> getClients(){
    //    return clientLoans.stream().map(loan -> loan.getClient()).collect(toList());
    //}
}
