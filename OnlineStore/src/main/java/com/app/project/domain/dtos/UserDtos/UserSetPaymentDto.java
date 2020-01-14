package com.app.project.domain.dtos.UserDtos;

import com.app.project.domain.entities.PaymentType;
import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class UserSetPaymentDto {

    @Expose
    private BigDecimal balance;

    @Expose
    private PaymentType paymentType;

    public UserSetPaymentDto(BigDecimal balance, PaymentType paymentType) {
        this.balance = balance;
        this.paymentType = paymentType;
    }

    public UserSetPaymentDto() {
    }

    @Positive
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @NotNull
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
