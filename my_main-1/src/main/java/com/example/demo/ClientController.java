package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;

@Controller
@RequestMapping({"/","/clients"})
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String showClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "add-client";
    }

    @PostMapping("/add")
    public String addClient(@Valid @ModelAttribute Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "add-client"; 
        }
        clientService.addClient(client);
        return "redirect:/clients"; 
    }

    @GetMapping("/update/{id}")
    public String showUpdateClientForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "update-client";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, @Valid @ModelAttribute Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "update-client";
        }
        client.setId(id);
        clientService.updateClient(client);
        return "redirect:/clients";
    }
    
    @PostMapping("/update/{id}/form")
    public String updateClientForm(@PathVariable Long id, @Valid @ModelAttribute Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "update-client";
        }
        client.setId(id);
        clientService.updateClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteClientForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "delete-client";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }
    
    @PostMapping("/upload/{id}")
    public String uploadDocument(@PathVariable Long id, @RequestParam("document") MultipartFile document) {
        Client client = clientService.getClientById(id);
        try {
            client.setDocument(document.getBytes());
            clientService.updateClient(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/clients";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client.getDocument() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "document_" + id + ".pdf");
            return new ResponseEntity<>(client.getDocument(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}