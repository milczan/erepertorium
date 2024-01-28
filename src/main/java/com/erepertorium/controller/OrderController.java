package com.erepertorium.controller;

import com.erepertorium.model.Order;
import com.erepertorium.repository.*;
import com.erepertorium.services.InvoiceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.net.URI;
import java.util.List;

@Controller


class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ClientTypeRepository clientTypeRepository;
    private final InvoiceRepository invoiceRepository;
    private final LanguageRepository languageRepository;
    private final OrderTypeRepository orderTypeRepository;
    private final StatusRepository statusRepository;
    private final TypeOfDocumentRepository typeOfDocumentRepository;
    final InvoiceService invoiceService;

    public OrderController(final OrderRepository orderRepository, final ClientRepository clientRepository, final ClientTypeRepository clientTypeRepository,
                    final InvoiceRepository invoiceRepository, final LanguageRepository languageRepository, final OrderTypeRepository orderTypeRepository,
                    final StatusRepository statusRepository, final TypeOfDocumentRepository typeOfDocumentRepository, final InvoiceService invoiceService) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.clientTypeRepository = clientTypeRepository;
        this.invoiceRepository = invoiceRepository;
        this.languageRepository = languageRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.statusRepository = statusRepository;
        this.typeOfDocumentRepository = typeOfDocumentRepository;
        this.invoiceService = invoiceService;
    }
    @GetMapping("/orders") String showOrders(Model model)
    {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders/index";
    }

    @GetMapping("/orders/new") String newOrder(Model model)
    {
        model.addAttribute("order", new Order());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("clientTypes",clientTypeRepository.findAll());
        model.addAttribute("invoices", invoiceRepository.findAll());
        model.addAttribute("languages", languageRepository.findAll());
        model.addAttribute("orderTypes", orderTypeRepository.findAll());
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("typeOfDocuments", typeOfDocumentRepository.findAll());

        return "orders/new";
    }

    @PostMapping("/orders/create")
    String createOrder(@ModelAttribute("order") @Valid Order toCreate, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("order", toCreate);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("clientTypes",clientTypeRepository.findAll());
            model.addAttribute("invoices", invoiceRepository.findAll());
            model.addAttribute("languages", languageRepository.findAll());
            model.addAttribute("orderTypes", orderTypeRepository.findAll());
            model.addAttribute("statuses", statusRepository.findAll());
            model.addAttribute("typeOfDocuments", typeOfDocumentRepository.findAll());
            return "orders/new"; // Return the form view again with validation errors
        }
        Order order = orderRepository.save(toCreate);

        return "redirect:/orders";
    }
    @DeleteMapping("/orders/{id}/destroy")
    String deleteOrder(@PathVariable ("id") int orderId, RedirectAttributes redirectAttributes, Model model){
        if (!orderRepository.existsById(orderId)){
            model.addAttribute("orders", orderRepository.findAll());
            redirectAttributes.addFlashAttribute("errorMessage", "Wystąpił błąd poczas usuwania zamówienia");
            return "order/index";
        }
        orderRepository.deleteById(orderId);
        redirectAttributes.addFlashAttribute("successMessage", "Zamówienie zostało usunięte");
        return "redirect:/orders";
    }

    @GetMapping ("/orders/{id}/edit")
    String editOrder(@PathVariable ("id") Integer orderId, Model model)
    {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderId));;
        model.addAttribute("order", order);
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("clientTypes", clientTypeRepository.findAll());
        model.addAttribute("invoices", invoiceRepository.findAll());
        model.addAttribute("languages", languageRepository.findAll());
        model.addAttribute("orderTypes", orderTypeRepository.findAll());
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("typeOfDocuments", typeOfDocumentRepository.findAll());

        return "orders/edit";
    }
    @PutMapping("/orders/{id}/update")
    String updateOrder(@ModelAttribute("order") @Valid Order toUpdate, @PathVariable ("id") Integer orderId, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("order", toUpdate);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("clientTypes",clientTypeRepository.findAll());
            model.addAttribute("invoices", invoiceRepository.findAll());
            model.addAttribute("languages", languageRepository.findAll());
            model.addAttribute("orderTypes", orderTypeRepository.findAll());
            model.addAttribute("statuses", statusRepository.findAll());
            model.addAttribute("typeOfDocuments", typeOfDocumentRepository.findAll());
            return "orders/new"; // Return the form view again with validation errors
        }
        if (!orderRepository.existsById(orderId)){
            return "orders/new";
        }
        toUpdate.setId(orderId);
        orderRepository.save(toUpdate);

        return "redirect:/orders";
    }
    @PostMapping("/orders/{orderId}/generate")
    public String generateInvoice(@PathVariable("orderId") int orderId, RedirectAttributes redirectAttributes) {
        try {
            invoiceService.generateInvoiceFromOrder(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Faktura została wygenerowana");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Wystąpił błąd poczas generowania faktury");
        }
        return "redirect:/orders";
    }

    @PostMapping("/api/orders")
    ResponseEntity<Order>createOrder(@RequestBody @Valid Order toCreate){
        Order result = orderRepository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


    @GetMapping(value = "/api/orders", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Order>> readAllOrders(){
        logger.warn("Exposing all the orders!");
        return ResponseEntity.ok(orderRepository.findAll());
    }
    @GetMapping("/api/orders")
        ResponseEntity<List<Order>> readAllOrders(Pageable page){
            logger.warn("Custom pageable");
            return ResponseEntity.ok(orderRepository.findAll(page).getContent());
    }

    @PutMapping("/api/orders/{id}")
    ResponseEntity<?> updateOrder(@PathVariable("id") int orderId, @RequestBody @Valid Order toUpdate){
        if (!orderRepository.existsById(orderId)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(orderId);
        orderRepository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
    @GetMapping ("/api/orders/{id}")
    ResponseEntity<Order> readOrder(@PathVariable ("id") int orderId)
    {
       return orderRepository.findById(orderId)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/api/orders/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable ("id") int orderId){
        if (!orderRepository.existsById(orderId)){
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(orderId);
        return ResponseEntity.noContent().build();
    }
}
