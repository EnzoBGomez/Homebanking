package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccountsDTO();
    AccountDTO getAccount(Long id);
    void saveAccount(Account account);
    String NumeroDeCuentaNoExistente(int min, int max, int digitos);
    Account findAccountByNumber(String number);
    boolean existsNumber(String number);
    void deleteAccount(Account account);
    Account findAccountById(Long id);
    Account cuentaConBalanceNecesario(Client client, Double amount);
}
