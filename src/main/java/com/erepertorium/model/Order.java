package com.erepertorium.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name ="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private Language language;

    public TypeOfDocument getTypeOfDocument() {
        return typeOfDocument;
    }

    public void setTypeOfDocument(final TypeOfDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }

    @ManyToOne
    @JoinColumn(name = "type_of_document_id", referencedColumnName = "id")
    private TypeOfDocument typeOfDocument;

    public OrderType getOrderType() {
        return orderType;
    }

   public void setOrderType(final OrderType orderType) {
        this.orderType = orderType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Boolean getPriority() {
        return isPriority;
    }

    @ManyToOne
    @JoinColumn(name = "order_type_id", referencedColumnName = "id")
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @NotBlank (message = "Number of repertorium must not be empty")
    private String numberOfRep;

   @Column(name="name", length = 50, nullable = false)
   @NotBlank (message = "Name of order must not be empty")
   private String name;
    @Column(name="date_of_order", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfOrder;

    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @Column(name="updated_at", nullable = false)
    private Date updatedAt;

    public Integer getId() {
        return id;

    }
    public void setId(final Integer id) {
        this.id = id;
    }
    public String getNumberOfRep() {
        return numberOfRep;
    }

   public void setNumberOfRep(String numberOfRepertorium) {
        numberOfRep = numberOfRepertorium;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

   public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

   public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNameOfAuthor() {
        return nameOfAuthor;
    }

    public void setNameOfAuthor(final String nameOfAuthor) {
        this.nameOfAuthor = nameOfAuthor;
    }

   public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(final Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

   public Integer getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(final Integer copyNumber) {
        this.copyNumber = copyNumber;
    }

   public BigDecimal getPrice() {
        return price;
    }

   public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(final Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(final String annotation) {
        this.annotation = annotation;
    }

    Boolean isPriority() {
        return isPriority;
    }

    public void setPriority(final Boolean priority) {
        isPriority = priority;
    }

    public Integer getTimeOfTranslation() {
        return timeOfTranslation;
    }

   public void setTimeOfTranslation(final Integer timeOfTranslation) {
        this.timeOfTranslation = timeOfTranslation;
    }

    @Column(name="name_of_author", length = 50)
    private String nameOfAuthor;
    @Column(name="number_of_pages")
    private Integer numberOfPages;
    @Column(name="copy_number")
    private Integer copyNumber;
    @Column(name="price", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    private BigDecimal price;
    @Column(name="date_of_receipt")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfReceipt;
    @Column(name="annotation", length = 1000)
    private String annotation;

    @Column(name = "is_priority")
    private Boolean isPriority;
    @Column(name="time_of_translation")
    private Integer timeOfTranslation;

    public Client getClient() {
        return client;
    }

    public void setClient(final Client client) {
        this.client = client;
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

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
}
