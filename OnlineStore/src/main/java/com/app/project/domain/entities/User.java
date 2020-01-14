package com.app.project.domain.entities;


import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Set;

@Entity
@Table(name="users")
public class User extends BaseEntity {


    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private Role role;
    private Payment payment;
    private Address address;
    private Set<Order> orders;
    private Set<User> friends;
    private Chat chat;


    public User() {
    }

    @Column(name="first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="email",unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id",referencedColumnName = "id")
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="address_id",referencedColumnName = "id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @OneToMany(targetEntity = Order.class,mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @ManyToMany()
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="friend_id",referencedColumnName = "id")
    )
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{ " +
                "user_id = " + getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @OneToOne(targetEntity = Chat.class,mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
