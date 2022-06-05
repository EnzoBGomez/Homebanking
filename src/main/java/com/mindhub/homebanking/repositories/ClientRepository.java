package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


//@CrossOrigin
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}
