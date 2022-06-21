package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    LoanService loanService;
    @Override
    public void saveClientLoan(LoanApplicationDTO loanApplicationDTO, Client client, Loan loan) {
//        Client client = clientService.getClientByEmail(authentication.getName());
//        Loan loan = loanService.getById(loanApplicationDTO.getIdLoan());
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()*(loan.getInterest()/100.0), loanApplicationDTO.getPayments(), client, loan);
        clientLoanRepository.save(clientLoan);

    }
}
