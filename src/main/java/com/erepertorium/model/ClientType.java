package com.erepertorium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="Client_types")
public class ClientType {
    public Integer getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String getName() {
        return name;
    }

    @Column(name="name", length = 100, nullable = false)
    @NotBlank(message = "Name of client type must not be empty")
    private String name;
}