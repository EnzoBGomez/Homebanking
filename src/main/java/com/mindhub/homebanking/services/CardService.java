package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CardPaymentDTO;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface CardService {
    void saveCard(Card card);
    String noRepeatNumberCard();
    Card getCardById(Long id);
    boolean existsCard(Long id);
    void deleteCard(Card card);
    Set<CardDTO> getCardsDto(Authentication authentication);
    Card getCardByNumber(String numberCard);
    void realizarPago(Account account, CardPaymentDTO cardPaymentDTO);
}
