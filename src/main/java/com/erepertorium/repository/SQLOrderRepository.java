package com.erepertorium.repository;

import com.erepertorium.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLOrderRepository extends OrderRepository, JpaRepository<Order, Integer> {
}
