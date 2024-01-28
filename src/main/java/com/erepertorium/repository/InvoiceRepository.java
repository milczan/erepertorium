package com.erepertorium.repository;

import com.erepertorium.model.Client;
import com.erepertorium.model.Invoice;
import com.erepertorium.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    List<Invoice> findAll();
    Page<Invoice> findAll(Pageable page);
    Optional<Invoice> findById(Integer id);
    Invoice save(Invoice entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}