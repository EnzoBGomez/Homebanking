package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindhub.homebanking.Utility.Utility.numeroDeDigitos;
import static java.util.stream.Collectors.toList;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccountsDTO() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @Override
    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public boolean existsNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    public void deleteAccount(Account account) {
        account.setActiveAccount(false);
        this.saveAccount(account);
    }

    @Override
    public Account findAccountById(Long id) {

        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account cuentaConBalanceNecesario(Client client, Double amount) {
        return client.getAccounts()
        .stream()
        .filter(accountt ->(accountt.isActiveAccount() && accountt.getBalance()>= amount))
        .findFirst()
        .orElse(null);
    }
    @Override
    public String NumeroDeCuentaNoExistente(int min, int max, int digitos) {
        String accountNumber;
        do {
            accountNumber = "VIN-"+ numeroDeDigitos(min, max, digitos);
        }while (accountRepository.existsByNumber(accountNumber));

        return accountNumber;
    }
}
