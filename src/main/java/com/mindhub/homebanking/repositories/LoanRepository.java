package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin
@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsById(Long Id);
}
