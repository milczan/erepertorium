package com.erepertorium.repository;

import com.erepertorium.model.Language;
import com.erepertorium.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLStatusRepository extends StatusRepository, JpaRepository<Status, Integer> {
}
