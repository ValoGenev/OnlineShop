package com.app.project.domain.dtos.PaymentDtos;

import com.app.project.domain.entities.PaymentType;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PaymentSeedDto {
    @Expose
    private BigDecimal balance;

    @Expose
    private PaymentType paymentType;

    @Expose
    private String serialNumber;

    @Expose
    private String expireTime;

    public PaymentSeedDto() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
