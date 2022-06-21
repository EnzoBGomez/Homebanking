package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;


    @Override
    public void newTransactionLoan(LoanApplicationDTO loanApplicationDTO) {
        Account account = accountService.findAccountByNumber(loanApplicationDTO.getAccountNumber());
        Transaction transaction = new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(), "loan approved",
               LocalDateTime.now(),account, account.getBalance() + loanApplicationDTO.getAmount());
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(account);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Account account) {
        account.getTransactions().forEach(transaction -> transaction.setTransactionActive(false));
        accountService.saveAccount(account);
    }
}
