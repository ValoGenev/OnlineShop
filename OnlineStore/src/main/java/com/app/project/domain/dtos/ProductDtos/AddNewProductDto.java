package com.app.project.domain.dtos.ProductDtos;

import com.app.project.domain.entities.Category;
import com.app.project.domain.entities.Order;
import com.app.project.domain.entities.ProductInShop;

import java.math.BigDecimal;
import java.util.Set;

public class AddNewProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private double discount;


    private ProductInShopAddDto productInShop;


    public AddNewProductDto(String name, String description, BigDecimal price, double discount,  ProductInShopAddDto productInShop) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.productInShop = productInShop;
    }

    public AddNewProductDto() {
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



    public ProductInShopAddDto getProductInShop() {
        return productInShop;
    }

    public void setProductInShop(ProductInShopAddDto productInShop) {
        this.productInShop = productInShop;
    }
}
