package com.app.project.domain.dtos.UserDtos;

import com.app.project.domain.entities.*;
import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class UserRegisterDto {

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
    private UserSetPaymentDto payment;

    private UserSetAddressDto address;

    public UserRegisterDto(String firstName,
                           String lastName, String email,
                           String password, String phoneNumber,
                           Gender gender, Role role,UserSetPaymentDto payment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.payment=payment;

    }

    public UserRegisterDto(String firstName, String lastName, String email, String password, String phoneNumber, Gender gender, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
    }
    public UserRegisterDto(){

    }

    @NotNull
    @Size(max = 40)
    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Size(max = 40)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    @Pattern(regexp = "[a-zA-z0-9._]+@[a-z]+\\.[a-z]{2,10}",message = "Email doesn't match the validations")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Password doesn't match the validations")
    @Size(min = 4,message = "Password must be more than 4 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(unique = true)
    @Pattern(regexp = "[0-9]{8,}", message = "phone must contain only digits and have more than 8 symbols")
    @Min(8)
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

    @NotNull
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }




    public UserSetAddressDto getAddress() {
        return address;
    }

    public void setAddress(UserSetAddressDto address) {
        this.address = address;
    }


    @NotNull
    public UserSetPaymentDto getPayment() {
        return payment;
    }

    public void setPayment(UserSetPaymentDto payment) {
        this.payment = payment;
    }
}
