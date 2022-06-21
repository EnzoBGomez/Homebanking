package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.Models.Card;
import com.mindhub.homebanking.Models.CardColor;
import com.mindhub.homebanking.Models.CardType;

import javax.persistence.Entity;
import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private String cvv;
    private LocalDateTime fromDate, thruDate;
    private String cardHolder, number;
    private CardType type;
    private CardColor color;
    private boolean cardActive;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.type = card.getType();
        this.color = card.getColor();
        this.cardActive = card.isCardActive();
    }

    public long getId() {
        return id;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public boolean isCardActive() {
        return cardActive;
    }

    public void setCardActive(boolean cardActive) {
        this.cardActive = cardActive;
    }
}
