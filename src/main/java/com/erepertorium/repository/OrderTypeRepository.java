package com.erepertorium.repository;

import com.erepertorium.model.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderTypeRepository {
    List<OrderType> findAll();
    Page<OrderType> findAll(Pageable page);
    Optional<OrderType> findById(Integer id);
    OrderType save(OrderType entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}

