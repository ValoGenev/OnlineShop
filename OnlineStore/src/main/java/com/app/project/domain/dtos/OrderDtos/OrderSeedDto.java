package com.app.project.domain.dtos.OrderDtos;

import com.app.project.domain.dtos.ProductDtos.ProductSeedDto;
import com.app.project.domain.entities.Product;
import com.app.project.domain.entities.User;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OrderSeedDto {

    @Expose
    private int id;

    @Expose
    private String purchaseDate;

    @Expose
    private Set<ProductSeedDto> products;


    private User user;

    public OrderSeedDto() {
    }


    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<ProductSeedDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductSeedDto> products) {
        this.products = products;
    }
}
