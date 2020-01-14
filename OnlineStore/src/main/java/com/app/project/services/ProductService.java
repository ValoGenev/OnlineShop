package com.app.project.services;

import com.app.project.domain.dtos.ProductDtos.ProductDiscountDto;
import com.app.project.domain.dtos.ProductDtos.ProductQuantityDto;
import com.app.project.domain.entities.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void seedProducts() throws IOException;

    List<Product> showAllProducts();

    void showDiscount();

    void setLoggedInUser(String email);

    void setLoggedOutUser();

    String changeProductDiscount(int id);

    void printShoppingCart();

    List<ProductQuantityDto> checkQuantity();

    List<ProductDiscountDto> checkDiscount();

    String changeProductQuantity(int id);

    List<Product> filterProducts(int[] filters);

    List<Product> checkOutOfStock();

    String addNewProduct();

    String deleteProduct(int id);


}
