package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> register(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Loan loan = loanRepository.findById(loanApplicationDTO.getIdLoan()).orElse(null);
        Client client = clientRepository.findByEmail(authentication.getName());
        Account accountDestiny = accountRepository.findByNumber(loanApplicationDTO.getAccountNumber());

        if(loanApplicationDTO.getAccountNumber() == null || loanApplicationDTO.getAmount() == null ||
                loanApplicationDTO.getPayments() == null || loanApplicationDTO.getIdLoan() == null){
            return new ResponseEntity<>("Datos erroneos", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount()<= 0 || loanApplicationDTO.getPayments() <= 0){
            return new ResponseEntity<>("Monto o cuotas invalidas",HttpStatus.FORBIDDEN);
        }
        if(loan == null){
            return new ResponseEntity<>("No existe el prestamo",HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("EL monto solicitado es superior al monto maximo", HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Las cuotas seleccionadas no existen",HttpStatus.FORBIDDEN);
        }
        if(!accountRepository.existsByNumber(loanApplicationDTO.getAccountNumber())){
            return new ResponseEntity<>("La cuenta de destino no existe",HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(accountDestiny)){
            return new ResponseEntity<>("Esta cuenta no le pertenece",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()*1.2, loanApplicationDTO.getPayments(), client, loan);
        Transaction transaction = new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(), "loan approved",
                LocalDateTime.now(),accountDestiny);

        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transaction);

        accountDestiny.setBalance(accountDestiny.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/loans")
    public Set<LoanDTO> getLoansDTO(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toSet());
    }
}
