package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardPaymentDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static com.mindhub.homebanking.Utility.Utility.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<?> createCard(Authentication authentication, @RequestParam String cardType, @RequestParam String cardColor) {

        CardType typeCard = CardType.CREDIT;
        CardColor colorCard = CardColor.GOLD;
        Client client = clientService.getClientByEmail(authentication.getName());

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
            cardService.saveCard(new Card(numeroDeDigitos(0,999,3),LocalDateTime.now(),LocalDateTime.now().plusYears(5),client,cardService.noRepeatNumberCard(),typeCard,colorCard));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Ya tiene 3 tarjetas de este tipo", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/clients/current/cards")
    public ResponseEntity<?> deleteCard(Authentication authentication, @RequestParam Long idCard){
        Client client = clientService.getClientByEmail(authentication.getName());
        if(idCard == null){
            return new ResponseEntity<>("ID INVALIDO", HttpStatus.FORBIDDEN);
        }
        if(!cardService.existsCard(idCard)){
            return new ResponseEntity<>("La tarjeta no existe", HttpStatus.FORBIDDEN);
        }
        Card card = cardService.getCardById(idCard);
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("La tarjeta no le pertenece", HttpStatus.FORBIDDEN);
        }
        if(!card.isCardActive()){
            return new ResponseEntity<>("La tarjeta ya se encuentra eliminada", HttpStatus.FORBIDDEN);
        }
        cardService.deleteCard(card);
        return new ResponseEntity<>("TARJETA ELIMINADA", HttpStatus.OK);
    }
    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication){

        return cardService.getCardsDto(authentication);
    }

    @PostMapping ("/cards/payments")
    @Transactional
    public ResponseEntity<?> makePayment(Authentication authentication, @RequestBody CardPaymentDTO cardPaymentDTO){
        Client client = clientService.getCurrentClient(authentication);
        if(cardPaymentDTO.getCardNumber().isEmpty() || cardPaymentDTO.getCvv().isEmpty() ||
                cardPaymentDTO.getDescription().isEmpty() || cardPaymentDTO.getAmount() == null){
            return new ResponseEntity<>("FALTAN DATOS", HttpStatus.FORBIDDEN);
        }
        Card card = cardService.getCardByNumber(cardPaymentDTO.getCardNumber());
        Account account = accountService.cuentaConBalanceNecesario(client, cardPaymentDTO.getAmount());
        if(card == null){
            return new ResponseEntity<>("LA TARJETA NO EXISTE", HttpStatus.FORBIDDEN);
        }
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("ESTA TARJETA NO LE PERTENECE", HttpStatus.FORBIDDEN);
        }
        if(Objects.equals(card.getThruDate(), LocalDateTime.now())){
            return new ResponseEntity<>("LA TARJETA ESTA VENCIDA", HttpStatus.FORBIDDEN);
        }
        if(!Objects.equals(card.getCvv(), cardPaymentDTO.getCvv())){
            return new ResponseEntity<>("EL CODIGO DE SEGURIDAD ES INCORRECTO", HttpStatus.FORBIDDEN);
        }
        if(account == null){
            return new ResponseEntity<>("NO TIENE TARJETAS CON SUFICIENTE SALDO", HttpStatus.FORBIDDEN);
        }
        cardService.realizarPago(account, cardPaymentDTO);
        return new ResponseEntity<>("PAGO REALIZADO", HttpStatus.OK);
    }
}
