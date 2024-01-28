package com.erepertorium.repository;

import com.erepertorium.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StatusRepository {
    List<Status> findAll();
    Page<Status> findAll(Pageable page);
    Optional<Status> findById(Integer id);
    Status save(Status entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
