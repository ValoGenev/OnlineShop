package com.app.project.domain.dtos.UserDtos;

import java.math.BigDecimal;

public class UserWithMostSpendedMoneyDto {

    private String firstName;
    private String lastName;
    private BigDecimal moneySpend;


    public UserWithMostSpendedMoneyDto(String firstName, String lastName, BigDecimal moneySpend) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneySpend = moneySpend;
    }

    public UserWithMostSpendedMoneyDto() {
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

    public BigDecimal getMoneySpend() {
        return moneySpend;
    }

    public void setMoneySpend(BigDecimal moneySpend) {
        this.moneySpend = moneySpend;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", moneySpend=" + moneySpend +
                '}';
    }
}
