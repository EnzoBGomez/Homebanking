package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.AccountType;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication, @RequestParam AccountType typeAccount ) {

        Client client = clientService.getCurrentClient(authentication);
        if(client.getAccounts().size() > 2)
            return new ResponseEntity<>("Ya tiene 3 cuentas", HttpStatus.FORBIDDEN);

        accountService.saveAccount(new Account(accountService.NumeroDeCuentaNoExistente(0, 99999999, 8)
                , LocalDateTime.now(),0, typeAccount, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        ClientDTO client = new ClientDTO(clientService.getCurrentClient(authentication));
        return new ArrayList<>(client.getAccounts());
    }
    @GetMapping("/clients/current/accounts/{id}")
    public ResponseEntity<?> getAccountDTO(Authentication authentication, @PathVariable Long id) {
        Client client = clientService.getCurrentClient(authentication);
        AccountDTO account = accountService.getAccount(id);
        if(account == null){
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(accountService.findAccountById(id))) {
            return new ResponseEntity<>("La cuenta no le pertenece", HttpStatus.FORBIDDEN);
        }
        if(!account.isActiveAccount()){
            return new ResponseEntity<>("La cuenta se encuentra deshabilitada", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<?> deleteAccount(Authentication authentication, @RequestParam String number){
        Client client = clientService.getCurrentClient(authentication);
        Account account = accountService.findAccountByNumber(number);

        if(number.isEmpty()){
            return new ResponseEntity<>("Falta enviar numero de cuenta", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("Esta cuenta no le pertenece", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().size() < 2){
            return new ResponseEntity<>("No puede eliminar la unica cuenta que tiene", HttpStatus.FORBIDDEN);
        }
        transactionService.deleteTransaction(account);
        accountService.deleteAccount(account);
        return new ResponseEntity<>("CUENTA ELIMINADA",HttpStatus.OK);
    }
}
