package com.mindhub.homebanking.Controller;


import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.dtos.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


import static com.mindhub.homebanking.Utility.Utility.noRepeatNumberAccount;
import static java.util.stream.Collectors.toList;
//@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClientController {
    private final LocalDateTime today = LocalDateTime.now();
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository repositoryAccount;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());

    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public int getRandomNumberUsingInts(int min, int max) {
//        Random random = new Random();
//        return random.ints(min, max)
//                .findFirst()
//                .getAsInt();
//    }


    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName,
                                           @RequestParam String lastName, @RequestParam String email,
                                           @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Ya se encuentra un usuario registrado con este mail", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        Account account = new Account(noRepeatNumberAccount(repositoryAccount,0,99999999, 8), today,0, client);
        repositoryAccount.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }



}
