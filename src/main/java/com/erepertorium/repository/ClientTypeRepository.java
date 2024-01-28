package com.erepertorium.repository;

import com.erepertorium.model.ClientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientTypeRepository {
    List<ClientType> findAll();
    Page<ClientType> findAll(Pageable page);
    Optional<ClientType> findById(Integer id);
    ClientType save(ClientType entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}

