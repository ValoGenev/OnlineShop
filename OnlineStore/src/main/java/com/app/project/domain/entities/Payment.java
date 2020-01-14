package com.app.project.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="payment")
public class Payment extends BaseEntity {

    private BigDecimal balance;
    private PaymentType paymentType;
    private String serialNumber;
    private String expireTime;
    private User user;

    public Payment() {
    }

    @Column(name="balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Column(name="payment_option")
    @Enumerated(EnumType.STRING)
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name="serial_number",nullable = false)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "card_exipire_date")
    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @OneToOne(targetEntity = User.class,mappedBy = "payment",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
