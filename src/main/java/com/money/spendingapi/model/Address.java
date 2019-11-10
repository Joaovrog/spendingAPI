package com.money.spendingapi.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String publicarea;
    private String number;
    private String type;
    private String neighborhood;
    private String zip;
    private String city;
    private String state;

    public String getPublicArea() {
        return publicarea;
    }

    public void setPublicArea(String publicArea) {
        this.publicarea = publicArea;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
