package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin
@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByNumber(String number);
    boolean existsByNumber(String number);
}
