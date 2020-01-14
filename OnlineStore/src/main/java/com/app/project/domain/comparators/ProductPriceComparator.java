package com.app.project.domain.comparators;

import com.app.project.domain.entities.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product product, Product product2) {
        return product.getPrice().compareTo(product2.getPrice());
    }
}
