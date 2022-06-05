package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utility.Utility.*;

@RestController
@RequestMapping("/api")
public class CardController {
    private final LocalDateTime today = LocalDateTime.now();
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam String cardType, @RequestParam String cardColor) {

        CardType typeCard = CardType.CREDIT;
        CardColor colorCard = CardColor.GOLD;
        Client client = clientRepository.findByEmail(authentication.getName());

        for(CardType myVar : CardType.values()){
            if(myVar.name().equals(cardType)){
                typeCard = myVar;
            }
        }
        for(CardColor myVar2 : CardColor.values()){
            if(myVar2.name().equals(cardColor)){
                colorCard = myVar2;
            }
        }

        if(client.getCards().stream().filter(card -> card.getType().name().equals(cardType)).count() <3){
            cardRepository.save(new Card(numeroDeDigitos(0,999,3),today,today.plusYears(5),client,noRepeatNumberCard(cardRepository),typeCard,colorCard));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Ya tiene 3 tarjetas de este tipo", HttpStatus.FORBIDDEN);
    }

}
