package com.erepertorium.repository;
import com.erepertorium.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLInvoiceRepository extends InvoiceRepository, JpaRepository<Invoice, Integer> {
}

