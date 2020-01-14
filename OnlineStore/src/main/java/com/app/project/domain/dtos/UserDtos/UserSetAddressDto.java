package com.app.project.domain.dtos.UserDtos;

import com.app.project.domain.entities.User;

import java.util.Set;

public class UserSetAddressDto {

    private String country;
    private String town;
    private String street;

    public UserSetAddressDto(String country, String town, String street) {
        this.country = country;
        this.town = town;
        this.street = street;
    }

    public UserSetAddressDto() {
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
