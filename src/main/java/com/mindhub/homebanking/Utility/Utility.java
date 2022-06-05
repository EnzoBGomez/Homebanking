package com.mindhub.homebanking.Utility;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;

import java.util.Random;
import java.util.stream.Collectors;

public final class Utility {


    private Utility() {
    }

    public static int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
    public static String numeroDeDigitos(int min, int max, int digitos){
        String numeroFinal;
        String numero = (""+getRandomNumberUsingInts(min,max));
        String aux = "";
        if(numero.length()<digitos){
            for (int i = 0; i<(digitos-numero.length()); i++ ){
                aux = aux + 0;
            }
        }
        numeroFinal = aux + numero;

        return numeroFinal;
    }
    public static String noRepeatNumberAccount(AccountRepository accountRepository, int min, int max, int digitos){
        String accountNumber;
        do {
            accountNumber = "VIN-"+ numeroDeDigitos(min, max, digitos);
        }while (accountRepository.existsByNumber(accountNumber));

        return accountNumber;
    }

    public static String noRepeatNumberCard(CardRepository cardRepository){
        String cardNumber;
        do {
            cardNumber = ""+numeroDeDigitos(0,9999,4)+""+numeroDeDigitos(0,9999,4)+""+numeroDeDigitos(0,9999,4)+""+numeroDeDigitos(0,9999,4);
        }while (cardRepository.existsByNumber(cardNumber));

        return cardNumber;
    }

}
