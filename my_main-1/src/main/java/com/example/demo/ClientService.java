package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public void addClient(Client client) {
        try {
            clientRepository.save(client);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    public void updateClient(Client client) {
        try {
            clientRepository.save(client);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteClient(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}