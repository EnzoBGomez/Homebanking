package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transaction(Authentication authentication, @RequestParam Double amount,
                                              @RequestParam String description,
                                              @RequestParam String numberOriginAccount,
                                              @RequestParam String numberDestinyAccount){

        Client client = clientRepository.findByEmail(authentication.getName());
        Account originAccount = accountRepository.findByNumber(numberOriginAccount);
        Account destinyAccount = accountRepository.findByNumber(numberDestinyAccount);

        if(amount == null || description.isEmpty() || numberOriginAccount.isEmpty() || numberDestinyAccount.isEmpty()){
            return new ResponseEntity<>("Datos incorrectos", HttpStatus.FORBIDDEN);
        }
        if(numberOriginAccount.equals(numberDestinyAccount)){
            return new ResponseEntity<>("No se puede enviar transacciones a la misma cuenta", HttpStatus.FORBIDDEN);
        }
        if(!accountRepository.existsByNumber(numberOriginAccount)){
            return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(originAccount)){
            return new ResponseEntity<>("No posee una cuenta con ese numero", HttpStatus.FORBIDDEN);
        }
        if(!accountRepository.existsByNumber(numberDestinyAccount)){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }
        if(originAccount.getBalance()< amount){
            return new ResponseEntity<>("No posee suficiente saldo para realizar esta operacion", HttpStatus.FORBIDDEN);
        }


        Transaction transactionOrigin = new Transaction(TransactionType.DEBITO,0-amount,
                description + "transferencia hecha a " + numberDestinyAccount,
                LocalDateTime.now(), originAccount);

        originAccount.setBalance((originAccount.getBalance()) - amount);
        accountRepository.save(originAccount);
        destinyAccount.setBalance((destinyAccount.getBalance()) + amount);
        accountRepository.save(destinyAccount);
        Transaction transactionDestiny = new Transaction(TransactionType.CREDITO, amount,
                description + "transferencia hecha por " + numberOriginAccount,
                LocalDateTime.now(), destinyAccount);

        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestiny);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
