package com.app.project.domain.dtos.ProductDtos;

import com.app.project.domain.entities.Order;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.Set;

public class ProductSeedDto {

    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    private BigDecimal price;

    @Expose
    private double discount;

    @Expose
    private boolean isInStock;

    @Expose
    private int quantity;

    @Expose int shopQuantity;

    public ProductSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getShopQuantity() {
        return shopQuantity;
    }

    public void setShopQuantity(int shopQuantity) {
        this.shopQuantity = shopQuantity;
    }
}
