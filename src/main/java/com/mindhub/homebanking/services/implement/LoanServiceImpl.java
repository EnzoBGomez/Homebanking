package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.NewLoanDTO;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan getById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Set<LoanDTO> getLoansDto() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toSet());
    }

    @Override
    public void createLoan(NewLoanDTO newLoanDTO) {
        Loan loan = new Loan(newLoanDTO.getTypeLoan(), newLoanDTO.getMaxAmount(), newLoanDTO.getPayments(), newLoanDTO.getInterest());
        loanRepository.save(loan);
    }


}
