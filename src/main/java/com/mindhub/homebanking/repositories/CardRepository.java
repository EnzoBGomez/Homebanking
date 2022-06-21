package com.mindhub.homebanking.repositories;


import com.mindhub.homebanking.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin
@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNumber(String number);
    Card findByNumber(String number);
}
