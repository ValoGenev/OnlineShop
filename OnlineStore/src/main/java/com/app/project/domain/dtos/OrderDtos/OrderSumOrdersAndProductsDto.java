package com.app.project.domain.dtos.OrderDtos;

import java.math.BigDecimal;

public class OrderSumOrdersAndProductsDto {

    private long purchases;
    private long productsSold;
    private BigDecimal profit;


    public OrderSumOrdersAndProductsDto(long purchases, long productsSold, BigDecimal profit) {
        this.purchases = purchases;
        this.productsSold = productsSold;
        this.profit = profit;
    }

    public long getPurchases() {
        return purchases;
    }

    public void setPurchases(long purchases) {
        this.purchases = purchases;
    }

    public long getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(long productsSold) {
        this.productsSold = productsSold;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
