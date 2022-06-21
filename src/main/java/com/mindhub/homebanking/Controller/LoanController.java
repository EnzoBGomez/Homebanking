package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.NewLoanDTO;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> registerLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Loan loan = loanService.getById(loanApplicationDTO.getIdLoan());
        Client client = clientService.getCurrentClient(authentication);
        Account accountDestiny = accountService.findAccountByNumber(loanApplicationDTO.getAccountNumber());

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
        if(!accountService.existsNumber(loanApplicationDTO.getAccountNumber())){
            return new ResponseEntity<>("La cuenta de destino no existe",HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(accountDestiny)){
            return new ResponseEntity<>("Esta cuenta no le pertenece",HttpStatus.FORBIDDEN);
        }
        clientLoanService.saveClientLoan(loanApplicationDTO, client, loan);
        transactionService.newTransactionLoan(loanApplicationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/loans")
    public Set<LoanDTO> getLoansDTO(){
        return loanService.getLoansDto();
    }

    @PostMapping("/admin/createLoans")
    public ResponseEntity<?> nuevoLoan(@RequestBody NewLoanDTO newLoanDTO){
        if(newLoanDTO.getMaxAmount() == null || newLoanDTO.getInterest() == null ||
                newLoanDTO.getTypeLoan().isEmpty() || newLoanDTO.getPayments().size() == 0){
            return new ResponseEntity<>("FALTAN DATOS", HttpStatus.FORBIDDEN);
        }
        if(newLoanDTO.getInterest() < 0 || newLoanDTO.getInterest() > 150){
            return new ResponseEntity<>("INTERES INVALIDO", HttpStatus.FORBIDDEN);
        }
        if(newLoanDTO.existeNumeroNegativoPayments()){
            return new ResponseEntity<>("LAS CUOTAS SON INVALIDAS", HttpStatus.FORBIDDEN);
        }
        loanService.createLoan(newLoanDTO);
        return new ResponseEntity<>("LOAN CREADO",HttpStatus.OK);
    }
}
