package com.app.project.repositories;

import com.app.project.domain.dtos.ProductDtos.ProductDiscountDto;
import com.app.project.domain.dtos.ProductDtos.ProductQuantityDto;
import com.app.project.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllBy();


    Set<Product> findAllByIdIn(int... id);

    List<Product> findAllByOrderByDiscountDesc();



    @Query("select new com.app.project.domain.dtos.ProductDtos.ProductQuantityDto(p.id,p.name,ps.quantity) " +
            "from Product as p " +
            "inner join p.productInShop as ps " +
            "order by ps.quantity")
    List<ProductQuantityDto> getProductQuantity();

    @Query("select new com.app.project.domain.dtos.ProductDtos.ProductDiscountDto(p.id,p.name,p.discount) " +
            "from Product as p")
    List<ProductDiscountDto> getProductDiscount();

    @Query("select p from Product as p inner join p.productInShop as ps where ps.quantity = 0")
    List<Product> getOutOfStockProducts();


    List<Product> findAllByOrderByProductInShop_Purchased();


    @Query("select new com.app.project.domain.dtos.ProductDtos.ProductProftMadeDto(p.name,ps.purchased,ps.moneyMade)  " +
            "from Product as p " +
            "inner join p.productInShop as ps")
    List<Product> findAllProductsProfit();




    @Transactional
    @Modifying// <- modifying query not selecting
    @Query("delete from Product p where p.id=:id")
    void deleteProductBy(@Param("id") int id);













}
