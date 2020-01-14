package com.app.project.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="online_shop")
public class ProductInShop extends BaseEntity{


    private int purchased;
    private int quantity;
    private BigDecimal moneyMade;
    private Set<Product> products;

    public ProductInShop() {
    }

    public ProductInShop(int purchased,int quantity,BigDecimal moneyMade){
        this.purchased=purchased;
        this.quantity=quantity;
        this.moneyMade=moneyMade;

    }

    @Column(name = "purchased_count")
    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "profit")
    public BigDecimal getMoneyMade() {
        return moneyMade;
    }

    public void setMoneyMade(BigDecimal moneyMade) {
        this.moneyMade = moneyMade;
    }

    @OneToMany(mappedBy = "productInShop",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
