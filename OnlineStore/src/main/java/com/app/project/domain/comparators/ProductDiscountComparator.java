package com.app.project.domain.comparators;

import com.app.project.domain.entities.Product;

import java.util.Comparator;

public class ProductDiscountComparator implements Comparator<Product> {

    @Override
    public int compare(Product product, Product product2) {
        return Double.compare(product.getDiscount(),product2.getDiscount());
    }
}
