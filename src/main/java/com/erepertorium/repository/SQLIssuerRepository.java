package com.erepertorium.repository;

import com.erepertorium.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLIssuerRepository extends IssuerRepository, JpaRepository <Issuer, Integer>{

}
