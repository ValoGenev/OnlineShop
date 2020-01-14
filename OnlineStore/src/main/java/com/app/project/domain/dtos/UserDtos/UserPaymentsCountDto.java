package com.app.project.domain.dtos.UserDtos;

public class UserPaymentsCountDto {


    private String firstName;
    private String lastName;
    private long count;

    public UserPaymentsCountDto(String firstName, String lastName, long count) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.count = count;
    }

    public UserPaymentsCountDto() {
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", count=" + count +
                '}';
    }
}
