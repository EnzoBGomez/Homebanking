package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }
    @Test
    public void existCardByNumber() {
        boolean existCardInBdByCardNumber = cardRepository.existsByNumber("1234567887654321");
        assertThat(existCardInBdByCardNumber, equalTo(true));
        existCardInBdByCardNumber = cardRepository.existsByNumber("does not exist this card number in bd");
        assertThat(existCardInBdByCardNumber, equalTo(false));
    }
    @Test
    public void existCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }
    @Test
    public void existClientByEmail() {
        Client client = clientRepository.findByEmail("melba@mindhub.com");
        assertThat(client, is(notNullValue()));
    }
    @Test
    public void existClients() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }
    @Test
    public void existTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(notNullValue()));
    }
    @Test
    public void transactionById() {
        Transaction transaction = transactionRepository.findById(1L).orElse(null);
        assertThat(transaction, is(notNullValue()));
    }
    @Test
    public void existAccountByNumber() {
        Account account = accountRepository.findByNumber("VIN-00000001");
        assertThat(account, is(notNullValue()));
    }
    @Test
    public void alreadyNumberAccount() {
        boolean alreadyExistInDb = accountRepository.existsByNumber("VIN-00000001");
        assertThat(alreadyExistInDb, equalTo(true));
    }
    @Test
    public void existsCLientLoans(){
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans, is(not(empty())));
    }
    @Test
    public void clientLoanById(){
        ClientLoan clientLoan = clientLoanRepository.findById(1L).orElse(null);
        assertThat(clientLoan, is(notNullValue()));
    }

}
