package com.erepertorium.services;
import com.erepertorium.model.Invoice;
import com.erepertorium.model.Issuer;
import com.erepertorium.model.Order;
import com.erepertorium.repository.InvoiceRepository;
import com.erepertorium.repository.IssuerRepository;
import com.erepertorium.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class InvoiceService {
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final IssuerRepository issuerRepository;

    public InvoiceService(OrderRepository orderRepository, InvoiceRepository invoiceRepository, IssuerRepository issuerRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.issuerRepository = issuerRepository;
    }

    @Transactional
    public void generateInvoiceFromOrder(int orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        Issuer issuer = issuerRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Invalid issuer ID"));


        // Tworzenie faktury na podstawie zamówienia
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setPrice(order.getPrice());
        invoice.setPaid(false); // Ustawienie, czy faktura jest opłacona
        invoice.setIssuer(issuer);

        // Zapis faktury
        invoiceRepository.save(invoice);
    }
}
