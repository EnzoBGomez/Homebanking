package com.mindhub.homebanking.Models;


import com.mindhub.homebanking.dtos.AccountDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;


    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client() { }

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail(){
        return email;
    }
    public void setEmail(String e_mail){
        this.email = e_mail;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }
    public long getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
//    public List<Loan> getLoans(){
//        return clientLoans.stream().map(client -> client.getLoan()).collect(toList());
//    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
    public Set<Card> getCards() {
        return cards;
    }
    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
