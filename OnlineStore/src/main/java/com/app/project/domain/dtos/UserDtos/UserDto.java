package com.app.project.domain.dtos.UserDtos;

import com.app.project.domain.dtos.AddressDtos.AddressSeedDto;
import com.app.project.domain.dtos.OrderDtos.OrderSeedDto;
import com.app.project.domain.dtos.PaymentDtos.PaymentSeedDto;
import com.app.project.domain.dtos.ProductDtos.ProductSeedDto;
import com.app.project.domain.entities.*;
import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String email;

    @Expose
    private String password;

    @Expose
    private String phoneNumber;

    @Expose
    private Gender gender;

    @Expose
    private Role role;

    @Expose
    private PaymentSeedDto payment;

    @Expose
    private AddressSeedDto address;

    @Expose
    private Set<OrderSeedDto> orders;



    public UserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public PaymentSeedDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentSeedDto payment) {
        this.payment = payment;
    }

    public AddressSeedDto getAddress() {
        return address;
    }

    public void setAddress(AddressSeedDto address) {
        this.address = address;
    }

    public Set<OrderSeedDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderSeedDto> orders) {
        this.orders = orders;
    }

}
