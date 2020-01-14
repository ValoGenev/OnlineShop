package com.app.project.domain.dtos.PaymentDtos;

import com.app.project.domain.entities.PaymentType;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class BalanceDto {


    private BigDecimal balance;

    private PaymentType paymentType;

    public BalanceDto(BigDecimal balance, PaymentType paymentType) {
        this.balance = balance;
        this.paymentType = paymentType;
    }

    public BalanceDto(){

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
}
