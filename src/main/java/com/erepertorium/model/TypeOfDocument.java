package com.erepertorium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="Type_of_documents")
public class TypeOfDocument {
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
    @NotBlank(message = "Name of document's type must not be empty")
    private String name;
}