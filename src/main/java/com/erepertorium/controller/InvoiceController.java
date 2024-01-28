package com.erepertorium.controller;
import com.erepertorium.model.Invoice;
import com.erepertorium.repository.InvoiceRepository;
import com.erepertorium.repository.OrderRepository;
import com.erepertorium.services.InvoiceService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.layout.font.FontProvider;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.thymeleaf.context.WebContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@Controller
class InvoiceController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    final InvoiceService invoiceService;

    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    InvoiceController(final InvoiceRepository invoiceRepository, final OrderRepository orderRepository, final InvoiceService invoiceService, final TemplateEngine templateEngine) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
        this.templateEngine = templateEngine;
    }
    @GetMapping("/invoices") String showInvoices(Model model)
    {
        model.addAttribute("invoices", invoiceRepository.findAll());
        return "invoices/index";
    }

    @GetMapping("/invoices/new") String newInvoice(Model model)
    {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("orders", orderRepository.findAll());

        return "invoices/new";
    }
    @PostMapping("/invoices/create")
    String createClient(@ModelAttribute("invoices") @Valid Invoice toCreate, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", toCreate);
            model.addAttribute("orders", orderRepository.findAll());

            return "invoices/new"; // Return the form view again with validation errors
        }
        invoiceService.generateInvoiceFromOrder(toCreate.getOrder().getId());

        return "redirect:/invoices";
    }
    @GetMapping("/invoices/{id}/pdf")
    public ResponseEntity<?> exportPdf(@PathVariable ("id") int invoiceId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + invoiceId));

        /* Create HTML using Thymeleaf template Engine */
        WebContext context = createContext(request, response);
        context.setVariable("invoice", invoice);
        String invoiceHtml = templateEngine.process("invoices/invoice", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(invoiceHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

    @DeleteMapping("/invoices/{id}/destroy")
    String deleteInvoice(@PathVariable ("id") int invoiceId, RedirectAttributes redirectAttributes, Model model){
        if (!invoiceRepository.existsById(invoiceId)){
            model.addAttribute("invoices", invoiceRepository.findAll());
            redirectAttributes.addFlashAttribute("errorMessage", "Wystąpił błąd poczas usuwania faktury");
            return "invoice/index";
        }
        invoiceRepository.deleteById(invoiceId);
        redirectAttributes.addFlashAttribute("successMessage", "Faktura została usunięta");
        return "redirect:/invoices";
    }
    @GetMapping ("/invoices/{id}/edit")
    String editInvoice(@PathVariable ("id") Integer invoiceId, Model model)
    {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + invoiceId));
        model.addAttribute("invoice", invoice);
        model.addAttribute("orders", orderRepository.findAll());

        return "invoices/edit";
    }
    @PutMapping("/invoices/{id}/update")
    String updateOInvoice(@ModelAttribute("invoice") @Valid Invoice toUpdate, @PathVariable ("id") Integer invoiceId, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", toUpdate);
            model.addAttribute("orders", orderRepository.findAll());

            return "invoices/new"; // Return the form view again with validation errors
        }
        if (!invoiceRepository.existsById(invoiceId)){
            return "invoices/new";
        }
        toUpdate.setId(invoiceId);
        invoiceRepository.save(toUpdate);

        return "redirect:/invoices";
    }

    @GetMapping(value = "/api/invoices", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Invoice>> readAllInvoice(){
        logger.warn("Exposing all the invoices!");
        return ResponseEntity.ok( invoiceRepository.findAll());
    }
    @GetMapping("/api/invoices")
    ResponseEntity<List<Invoice>> readAllInvoices(Pageable page){
        logger.warn("Custom pageable");
        return ResponseEntity.ok( invoiceRepository.findAll(page).getContent());
    }

    @PutMapping("/api/invoices/{id}")
    ResponseEntity<?> updateInvoices(@PathVariable("id") int invoiceId, @RequestBody @Valid Invoice toUpdate){
        if (! invoiceRepository.existsById(invoiceId)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(invoiceId);
        invoiceRepository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
    @GetMapping ("/api/invoices/{id}")
    ResponseEntity<Invoice> readInvoice(@PathVariable ("id") int invoiceId)
    {
        return  invoiceRepository.findById(invoiceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/api/invoices/{id}")
    ResponseEntity<?> deleteInvoice(@PathVariable ("id") int invoiceId){
        if (! invoiceRepository.existsById(invoiceId)){
            return ResponseEntity.notFound().build();
        }
        invoiceRepository.deleteById(invoiceId);
        return ResponseEntity.noContent().build();
    }

    public static WebContext createContext(HttpServletRequest req, HttpServletResponse res) {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(req.getServletContext());
        IServletWebExchange exchange = application.buildExchange(req, res);
        return new WebContext(exchange);
    }
}