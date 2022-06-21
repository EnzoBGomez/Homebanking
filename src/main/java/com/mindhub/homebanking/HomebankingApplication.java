package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.mindhub.homebanking.Models.TransactionType.CREDITO;
import static com.mindhub.homebanking.Models.TransactionType.DEBITO;

@SpringBootApplication
public class HomebankingApplication {

    @Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	private final LocalDateTime today = LocalDateTime.now();
	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository repositoryAccount, TransactionRepository repositoryTransaction, LoanRepository repositoryLoan, ClientLoanRepository repositoryClientLoan, CardRepository repositoryCard) {
		return (args) -> {
			// save a couple of customers
			Client client1 = new Client("Melba","Morel","melba@mindhub.com",passwordEncoder.encode("contra123"));
            Client client2 = new Client("Melba2","More2l","melb2a@admin.com",passwordEncoder.encode("contraadmin"));
			repositoryClient.save(client1);
			repositoryClient.save(client2);

			Account account1 = new Account("VIN-00000001",today, 5000, AccountType.AHORRO, client1);
			Account account2 = new Account("VIN-00000002",today.plusDays(1), 7500, AccountType.AHORRO, client1);
			repositoryAccount.save(account1);
			repositoryAccount.save(account2);

			Transaction transaction1 = new Transaction(TransactionType.DEBITO, -20000, "gabinete", today.plusDays(2), account1, account1.getBalance() - 2000);
			Transaction transaction2 = new Transaction(TransactionType.CREDITO, 4000, "regalo", today.plusDays(1), account1, account1.getBalance() + 4000);
			Transaction transaction3 = new Transaction(TransactionType.DEBITO, -500, "cena",today,account2, account2.getBalance() - 500);
			Transaction transaction4 = new Transaction(TransactionType.CREDITO, 8000, "aguinaldo", today, account2, account2.getBalance() + 8000);
			repositoryTransaction.save(transaction1);
			repositoryTransaction.save(transaction2);
			repositoryTransaction.save(transaction3);
			repositoryTransaction.save(transaction4);

			Loan loan1 = new Loan("Hipotecario",500000.000,Arrays.asList(12,24,36,48,60), 25F);
			Loan loan2 = new Loan("Personal",100000.000, Arrays.asList(6,12,24), 35F);
			Loan loan3 = new Loan("Automotriz",300000.000, Arrays.asList(6,12,24,36), 40F);
			repositoryLoan.save(loan1);
			repositoryLoan.save(loan2);
			repositoryLoan.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.000, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.000, 12, client1, loan2);
			repositoryClientLoan.save(clientLoan1);
			repositoryClientLoan.save(clientLoan2);

			Card card1 = new Card("321",today,today,client1,"1234567887654321",CardType.DEBIT,CardColor.GOLD);
			Card card2 = new Card("326",today,today.plusYears(5),client1,"1234567887653321",CardType.CREDIT,CardColor.TITANIUM);
			repositoryCard.save(card1);
			repositoryCard.save(card2);
		};
	}

}
