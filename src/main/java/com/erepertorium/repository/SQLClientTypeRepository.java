package com.erepertorium.repository;

import com.erepertorium.model.Client;
import com.erepertorium.model.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLClientTypeRepository extends ClientTypeRepository, JpaRepository<ClientType, Integer> {
}