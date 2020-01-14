package com.app.project.domain.entities;

import org.springframework.data.jpa.repository.query.Procedure;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="orders")
public class Order extends BaseEntity{

    private User user;
    private Set<Product> products;
    private LocalDate purchaseDate;

//    private BigDecimal price;
//    private String purchaseDate;
//    private String shipDate;

      public Order(){

      }


    @Column(name="purchase_date")
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }



    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany(targetEntity = Product.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="products_orders",
    joinColumns = @JoinColumn(name="order_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="product_id",referencedColumnName = "id"))
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
