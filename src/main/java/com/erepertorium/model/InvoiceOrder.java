package com.erepertorium.model;

import jakarta.persistence.*;


@Entity
@Table(name ="InvoiceOrders")
public class InvoiceOrder {
    @EmbeddedId
    private InvoiceOrderId invoiceOrderId;

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    public Invoice getInvoice() {
        return invoice;
    }

   public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    Invoice invoice;

}

