package com.erepertorium.repository;
import com.erepertorium.model.Issuer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IssuerRepository {
    List<Issuer> findAll();
    Page<Issuer> findAll(Pageable page);
    Optional<Issuer> findById(Integer id);
    Issuer save(Issuer entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
