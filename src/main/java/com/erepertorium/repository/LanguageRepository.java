package com.erepertorium.repository;

import com.erepertorium.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository {
    List<Language> findAll();
    Page<Language> findAll(Pageable page);
    Optional<Language> findById(Integer id);
    Language save(Language entity);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}

