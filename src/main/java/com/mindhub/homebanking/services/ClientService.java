package com.mindhub.homebanking.services;

import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.dtos.ClientDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientsDTO();
    Client getCurrentClient(Authentication authentication);
    ClientDTO getClient(Long id);
    void saveClient(Client client);
    Client getClientByEmail(String email);
}
