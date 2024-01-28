package com.erepertorium.repository;

import com.erepertorium.model.Client;
import com.erepertorium.model.Invoice;
import com.erepertorium.model.TypeOfDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypeOfDocumentRepository {
    List<TypeOfDocument> findAll();
    Page<TypeOfDocument> findAll(Pageable page);
    Optional<TypeOfDocument> findById(Integer id);
    TypeOfDocument save(TypeOfDocument entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}

