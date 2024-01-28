package com.erepertorium.repository;

import com.erepertorium.model.Language;
import com.erepertorium.model.TypeOfDocument;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLTypeOfDocumentRepository extends TypeOfDocumentRepository, JpaRepository<TypeOfDocument, Integer> {
}
