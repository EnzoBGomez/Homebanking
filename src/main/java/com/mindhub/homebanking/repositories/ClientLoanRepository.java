package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
}
