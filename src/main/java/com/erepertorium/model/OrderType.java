package com.erepertorium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="Order_types")
public class OrderType {
   public Integer getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String getName() {
        return name;
    }

    @Column(name="name", length = 50, nullable = false)
    @NotBlank(message = "Name of orders type must not be empty")
    private String name;
}