package com.erepertorium.repository;

import com.erepertorium.model.Language;
import com.erepertorium.model.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLOrderTypeRepository extends OrderTypeRepository, JpaRepository<OrderType, Integer> {
}

