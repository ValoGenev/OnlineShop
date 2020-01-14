package com.app.project.domain.dtos.ProductDtos;

public class ProductQuantityDto {

    private int id;
    private String name;
    private int quantity;

    public ProductQuantityDto(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public ProductQuantityDto() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductQuantityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
