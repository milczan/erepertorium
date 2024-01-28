package com.erepertorium.controller;

import com.erepertorium.model.Client;
import com.erepertorium.repository.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.List;


@Controller
class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientRepository clientRepository;
    private final ClientTypeRepository clientTypeRepository;

    ClientController( final ClientRepository clientRepository, final ClientTypeRepository clientTypeRepository) {
        this.clientRepository = clientRepository;
        this.clientTypeRepository = clientTypeRepository;
    }
    @GetMapping("/clients") String showClients(Model model)
    {
        model.addAttribute("clients", clientRepository.findAll());
        return "clients/index";
    }
    @GetMapping("/clients/new") String newClient(Model model)
    {
        model.addAttribute("client", new Client());
        model.addAttribute("clientTypes",clientTypeRepository.findAll());

        return "clients/new";
    }

    @PostMapping("/clients/create")
    String createClient(@ModelAttribute("client") @Valid Client toCreate, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", toCreate);
            model.addAttribute("clientTypes",clientTypeRepository.findAll());

            return "clients/new"; // Return the form view again with validation errors
        }
        Client client = clientRepository.save(toCreate);

        return "redirect:/clients";
    }
    @DeleteMapping("/clients/{id}/destroy")
    String deleteClient(@PathVariable ("id") int clientId, RedirectAttributes redirectAttributes, Model model){
        if (!clientRepository.existsById(clientId)){
            model.addAttribute("clients", clientRepository.findAll());
            redirectAttributes.addFlashAttribute("errorMessage", "Wystąpił błąd poczas usuwania klienta");
            return "client/index";
        }
        clientRepository.deleteById(clientId);
        redirectAttributes.addFlashAttribute("successMessage", "Zamówienie zostało usunięte");
        return "redirect:/clients";
    }
    @GetMapping ("/clients/{id}/edit")
    String editClient(@PathVariable ("id") Integer clientId, Model model)
    {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + clientId));;
        model.addAttribute("client", client);
        model.addAttribute("clientTypes",clientTypeRepository.findAll());

        return "clients/edit";
    }
    @PutMapping("/clients/{id}/update")
    String updateOClient(@ModelAttribute("client") @Valid Client toUpdate, @PathVariable ("id") Integer clientId, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", toUpdate);
            model.addAttribute("clientTypes",clientTypeRepository.findAll());

            return "clients/new"; // Return the form view again with validation errors
        }
        if (!clientRepository.existsById(clientId)){
            return "clients/new";
        }
        toUpdate.setId(clientId);
       clientRepository.save(toUpdate);

        return "redirect:/clients";
    }
    @PostMapping("/api/clients")
    ResponseEntity<Client> createClient(@RequestBody @Valid Client toCreate){
       Client result = clientRepository.save (toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(value = "/api/clients", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Client>> readAllClient(){
        logger.warn("Exposing all the clients!");
        return ResponseEntity.ok(clientRepository.findAll());
    }
    @GetMapping("/api/clients")
    ResponseEntity<List<Client>> readAllClients(Pageable page){
        logger.warn("Custom pageable");
        return ResponseEntity.ok(clientRepository.findAll(page).getContent());
    }

    @PutMapping("/api/clients/{id}")
    ResponseEntity<?> updateClients(@PathVariable("id") int clientId, @RequestBody @Valid Client toUpdate){
        if (!clientRepository.existsById(clientId)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(clientId);
        clientRepository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
    @GetMapping ("/api/clients/{id}")
    ResponseEntity<Client> readClient(@PathVariable ("id") int clientId)
    {
        return clientRepository.findById(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/api/clients/{id}")
    ResponseEntity<?> deleteClient(@PathVariable ("id") int clientId){
        if (!clientRepository.existsById(clientId)){
            return ResponseEntity.notFound().build();
        }
        clientRepository.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }
}