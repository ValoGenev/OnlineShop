package com.app.project.domain.dtos.ProductDtos;

import java.math.BigDecimal;

public class ProductProftMadeDto {

    private String name;
    private int purchased;
    private BigDecimal profit;

    public ProductProftMadeDto(String name, int purchased, BigDecimal profit) {
        this.name = name;
        this.purchased = purchased;
        this.profit = profit;
    }

    public ProductProftMadeDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
