package com.app.project.repositories;

import com.app.project.domain.dtos.OrderDtos.OrderSumOrdersAndProductsDto;
import com.app.project.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Transactional
    @Modifying
    @Query("delete from Order o where o.id=:id")
    void deleteOrder(@Param("id") int id);


    @Query("select sum(p.price) " +
            "from Order as o " +
            "inner join o.products as p " +
            "group by o.id " +
            "having o.id=:id")
    Object[] getProductsPriceSum(@Param("id") int id);


    @Query("select sum(p.price) " +
            "from Order as o " +
            "inner join o.products as p " +
            "where o.purchaseDate >= :localDate")
    BigDecimal getProfitAfterDate(@Param("localDate") LocalDate localDate);


    @Query("select sum(p.price) " +
            "from Order as o " +
            "inner join o.products as p " +
            "where o.purchaseDate <= :localDate")
    BigDecimal getProfitBeforeDate(@Param("localDate") LocalDate localDate);



    @Query("select new com.app.project.domain.dtos.OrderDtos.OrderSumOrdersAndProductsDto(count(o),count(p),sum(p.price)) " +
            "from Order as o " +
            "inner join o.products as p ")
    OrderSumOrdersAndProductsDto getSumOrdersAndProducts();

}
