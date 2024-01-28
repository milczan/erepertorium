package com.erepertorium.repository;
import com.erepertorium.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Page<Order>findAll(Pageable page);
   Optional<Order> findById(Integer id);
   Order save(Order entity);

    void deleteById(Integer id);

   boolean existsById(Integer id);
}
