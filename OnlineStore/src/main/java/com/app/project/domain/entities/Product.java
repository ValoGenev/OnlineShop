package com.app.project.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="products")
public class Product  extends BaseEntity {


    private String name;
    private String description;
    private BigDecimal price;
    private double discount;
    private ProductInShop productInShop;
    private Set<Order> orders;
    private Set<Category> categories;



    public Product() {
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name="price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    @Column(name="black_friday_discount")
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @ManyToMany(targetEntity = Order.class,mappedBy = "products",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "products_categories",
    joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="category_id",referencedColumnName = "id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(" | ");
        getCategories().forEach(category -> {
            sb.append(category.getName()).append(", ");
        });

        return String.format("(%d) | Name: %s | Description: %s | Price: %s | discount: %f | ",
                getId(),name,description,price.toString(),discount) + sb;
    }


    @ManyToOne(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="shop_info_id",referencedColumnName = "id")
    public ProductInShop getProductInShop() {
        return productInShop;
    }

    public void setProductInShop(ProductInShop productInShop) {
        this.productInShop = productInShop;
    }
}
