package com.app.project.domain.dtos.AddressDtos;

import com.google.gson.annotations.Expose;

public class AddressSeedDto {

    @Expose
    private String country;

    @Expose
    private String town;

    @Expose
    private String street;


    public AddressSeedDto() {
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
