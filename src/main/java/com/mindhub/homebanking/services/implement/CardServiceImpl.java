package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardPaymentDTO;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utility.Utility.numeroDeDigitos;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public String noRepeatNumberCard() {
        String cardNumber;
        do {
            cardNumber = ""+numeroDeDigitos(0,9999,4)+""+numeroDeDigitos(0,9999,4)+""+numeroDeDigitos(0,9999,4)+""+numeroDeDigitos(0,9999,4);
        }while (cardRepository.existsByNumber(cardNumber));

        return cardNumber;
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.getById(id);
    }

    @Override
    public boolean existsCard(Long id) {
        return cardRepository.existsById(id);
    }

    @Override
    public void deleteCard(Card card) {
        card.setCardActive(false);
        this.saveCard(card);
    }
    @Override
    public Set<CardDTO> getCardsDto(Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication);
        return client.getCards().stream().filter(Card::isCardActive).map(CardDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Card getCardByNumber(String numberCard) {
        return cardRepository.findByNumber(numberCard);
    }

    @Override
    public void realizarPago(Account account, CardPaymentDTO cardPaymentDTO) {
        Transaction transaction = new Transaction(TransactionType.DEBITO,cardPaymentDTO.getAmount(),cardPaymentDTO.getDescription(),
                LocalDateTime.now(), account, account.getBalance()- cardPaymentDTO.getAmount());
        transactionService.saveTransaction(transaction);
        accountService.saveAccount(account);

    }
}
