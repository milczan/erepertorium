package com.erepertorium.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class InvoiceOrderId implements Serializable {
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "invoice_id")
    private Integer invoiceId;

    // default constructor

    public InvoiceOrderId(Integer orderId, Integer invoiceId) {
        this.orderId = orderId;
        this.invoiceId = invoiceId;
    }

    // getters, equals() and hashCode() methods
}
