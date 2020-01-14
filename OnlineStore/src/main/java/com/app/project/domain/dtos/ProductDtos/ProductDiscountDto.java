package com.app.project.domain.dtos.ProductDtos;

public class ProductDiscountDto {
    private int id;
    private String name;
    private double discount;

    public ProductDiscountDto(int id, String name, double discount) {
        this.id = id;
        this.name = name;
        this.discount = discount;
    }

    public ProductDiscountDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return discount;
    }

    public void setQuantity(int quantity) {
        this.discount = quantity;
    }

    @Override
    public String toString() {
        return "ProductDiscountDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }
}
