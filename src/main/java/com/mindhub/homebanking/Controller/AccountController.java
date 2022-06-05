package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utility.Utility.noRepeatNumberAccount;
import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class AccountController {
    private final LocalDateTime today = LocalDateTime.now();
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }


    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication ) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if(client.getAccounts().size() >2)
            return new ResponseEntity<>("Ya tiene 3 cuentas", HttpStatus.FORBIDDEN);

        accountRepository.save(
                new Account(noRepeatNumberAccount(accountRepository, 0, 99999999, 8)
                , today,0, client));
        return new ResponseEntity<>(HttpStatus.CREATED);



    }
    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        ClientDTO client = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        return new ArrayList<>(client.getAccounts());
    }
//    @RequestMapping("/clients/current/accounts/{id}")
//    public AccountDTO getAccountDTO(@PathVariable long id){
//
//
//    }
}
