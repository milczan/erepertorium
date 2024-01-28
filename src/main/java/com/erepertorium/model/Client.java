package com.erepertorium.model;

import jakarta.persistence.*;

@Entity
@Table(name ="Clients")
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public ClientType getClientType() {
        return clientType;
    }

    @ManyToOne
    @JoinColumn(name = "client_type_id", referencedColumnName = "id")
    private ClientType clientType;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public void setClientType(final ClientType clientType) {
        this.clientType = clientType;
    }

    @Column(name="company_name", length = 100)
    private String companyName;
    @Column(name="first_name", length = 50)
    private String firstName;
    @Column(name="surname", length = 50)
    private String surname;
    @Column(name="NIP")
    private Integer NIP;
    @Column(name="REGON")
    private Integer REGON;
    @Column(name="street", length = 50, nullable = false)
    private String street;
    @Column(name="city", length = 50, nullable = false)
    private String city;
    @Column(name="house_number", nullable = false)
    private Integer houseNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Integer getNIP() {
        return NIP;
    }

    public void setNIP(final Integer NIP) {
        this.NIP = NIP;
    }
    public Integer getREGON() {
        return REGON;
    }

    public void setREGON(final Integer REGON) {
        this.REGON = REGON;
    }

   public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

   public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(final Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final Integer postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name="apartment_number")
    private Integer apartmentNumber;
    @Column(name="postal_code", nullable = false)
    private Integer postalCode;

    public String getClientFullName(){
        if (clientType.getId() ==2){
            return getFirstName() +' '+ getSurname();
        }else {
            return getCompanyName();
        }
    }
}