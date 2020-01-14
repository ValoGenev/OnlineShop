package com.app.project.domain.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="addresses")
public class Address extends BaseEntity{


    private String country;
    private String town;
    private String street;
    private Set<User> users;


    public Address() {

    }

    @Column
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @OneToMany(targetEntity = User.class,mappedBy = "address",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
