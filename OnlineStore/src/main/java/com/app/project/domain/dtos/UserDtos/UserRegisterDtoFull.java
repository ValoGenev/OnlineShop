package com.app.project.domain.dtos.UserDtos;

import com.app.project.domain.dtos.UserDtos.UserSetPaymentDto;
import com.app.project.domain.entities.Address;
import com.app.project.domain.entities.Gender;
import com.app.project.domain.entities.Role;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDtoFull {
    private String firstName;


    private String lastName;


    private String email;


    private String password;


    private String phoneNumber;


    private Gender gender;


    private Role role;

    private UserSetPaymentDto payment;

    private Address address;

    public UserRegisterDtoFull(String firstName,
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
        this.address = null;
        this.payment=payment;

    }

    public UserRegisterDtoFull(String firstName, String lastName, String email, String password, String phoneNumber, Gender gender, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public UserSetPaymentDto getPayment() {
        return payment;
    }

    public void setPayment(UserSetPaymentDto payment) {
        this.payment = payment;
    }
}
