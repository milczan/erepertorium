package com.erepertorium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="Invoices")
public class Invoice {
    public Integer getId() {
        return id;

    }
    public void setId(final Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(final Boolean paid) {
        this.paid = paid;
    }

   public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="price", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    private BigDecimal price;
    @Column(name="paid", nullable = false)
    private Boolean paid;
    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @Column(name="updated_at", nullable = false)
    private Date updatedAt;


    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(final Issuer issuer) {
        this.issuer = issuer;
    }

    @ManyToOne
    @JoinColumn(name = "issuer_id", referencedColumnName = "id")
    private Issuer issuer;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
        createdAt = new Date();
    }

    public String getClientFullAddress(){
        Order order = getOrder();
        Client client = order.getClient();
        if ((client.getClientType().getId() ==2))
        {
            return   client.getStreet() +' '+ client.getHouseNumber() +' '+ client.getApartmentNumber() +' '+ client.getPostalCode() +' '+ client.getCity();
        }else {
            return   client.getStreet() +' '+ client.getHouseNumber() +' '+ client.getApartmentNumber() +' '+ client.getPostalCode() +' '+ client.getCity() + ' '+ "NIP" + ' ' + client.getNIP() + ' ' + "REGON" + ' ' + client.getREGON();
        }
    }
}

