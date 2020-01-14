package com.app.project.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    private String name;
    private Set<Product> products;


    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
