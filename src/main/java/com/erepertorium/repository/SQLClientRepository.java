package com.erepertorium.repository;

import com.erepertorium.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


interface SQLClientRepository extends ClientRepository, JpaRepository<Client, Integer> {
}

