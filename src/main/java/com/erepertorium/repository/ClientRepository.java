package com.erepertorium.repository;
import com.erepertorium.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();
    Page<Client> findAll(Pageable page);
    Optional<Client> findById(Integer id);
    Client save(Client entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
