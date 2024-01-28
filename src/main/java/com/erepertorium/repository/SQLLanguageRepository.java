package com.erepertorium.repository;

import com.erepertorium.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

interface SQLLanguageRepository extends LanguageRepository,JpaRepository<Language, Integer>{
}
