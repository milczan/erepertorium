package com.erepertorium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="Languages")
public class Language {
   public Integer getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String getName() {
        return name;
    }

    @Column(name="name", length = 20, nullable = false)
    @NotBlank (message = "Name of language must not be empty")
    private String name;
}
