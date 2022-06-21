package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import org.springframework.security.core.Authentication;

public interface ClientLoanService {

    void saveClientLoan(LoanApplicationDTO loanApplicationDTO, Client client, Loan loan);
}
