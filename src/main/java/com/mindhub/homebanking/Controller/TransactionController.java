package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<?> transaction(Authentication authentication, @RequestParam Double amount,
                                              @RequestParam String description,
                                              @RequestParam String numberOriginAccount,
                                              @RequestParam String numberDestinyAccount){

        Client client = clientService.getCurrentClient(authentication);
        Account originAccount = accountService.findAccountByNumber(numberOriginAccount);
        Account destinyAccount = accountService.findAccountByNumber(numberDestinyAccount);

        if(amount == null || description.isEmpty() || numberOriginAccount.isEmpty() || numberDestinyAccount.isEmpty()){
            return new ResponseEntity<>("Datos incorrectos", HttpStatus.FORBIDDEN);
        }
        if(numberOriginAccount.equals(numberDestinyAccount)){
            return new ResponseEntity<>("No se puede enviar transacciones a la misma cuenta", HttpStatus.FORBIDDEN);
        }
        if(!accountService.existsNumber(numberOriginAccount)){
            return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(originAccount)){
            return new ResponseEntity<>("No posee una cuenta con ese numero", HttpStatus.FORBIDDEN);
        }
        if(!accountService.existsNumber(numberDestinyAccount)){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }
        if(originAccount.getBalance()< amount){
            return new ResponseEntity<>("No posee suficiente saldo para realizar esta operacion", HttpStatus.FORBIDDEN);
        }


        Transaction transactionOrigin = new Transaction(TransactionType.DEBITO,0-amount,
                description + " transferencia hecha a " + numberDestinyAccount,
                LocalDateTime.now(), originAccount, originAccount.getBalance() - amount);

        originAccount.setBalance((originAccount.getBalance()) - amount);
        accountService.saveAccount(originAccount);
        destinyAccount.setBalance((destinyAccount.getBalance()) + amount);
        accountService.saveAccount(destinyAccount);
        Transaction transactionDestiny = new Transaction(TransactionType.CREDITO, amount,
                description + " transferencia hecha por " + numberOriginAccount,
                LocalDateTime.now(), destinyAccount, destinyAccount.getBalance() + amount);

        transactionService.saveTransaction(transactionOrigin);
        transactionService.saveTransaction(transactionDestiny);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
