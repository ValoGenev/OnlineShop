package com.app.project.domain.dtos.ProductDtos;

import java.math.BigDecimal;

public class ProductInShopAddDto {

    private int purchased;
    private int quantity;
    private BigDecimal moneyMade;

    public ProductInShopAddDto() {
    }

    public ProductInShopAddDto( int quantity) {
        this.purchased = 0;
        this.quantity = quantity;
        this.moneyMade = BigDecimal.valueOf(0);
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getMoneyMade() {
        return moneyMade;
    }

    public void setMoneyMade(BigDecimal moneyMade) {
        this.moneyMade = moneyMade;
    }
}
