package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.NewLoanDTO;

import java.util.Set;

public interface LoanService {
    Loan getById(Long id);
    Set<LoanDTO> getLoansDto();
    void createLoan(NewLoanDTO newLoanDTO);
}
