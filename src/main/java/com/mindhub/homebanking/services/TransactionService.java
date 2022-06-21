package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;

public interface TransactionService {
    void newTransactionLoan(LoanApplicationDTO loanApplicationDTO);
    void saveTransaction(Transaction transaction);
    void deleteTransaction(Account account);
}
