package com.app.project.services.impl;

import com.app.project.domain.dtos.OrderDtos.OrderSumOrdersAndProductsDto;
import com.app.project.domain.entities.Order;
import com.app.project.domain.entities.User;
import com.app.project.repositories.OrderRepository;
import com.app.project.repositories.UserRepository;
import com.app.project.services.OrderService;
import com.app.project.util.FileUtil;
import com.app.project.util.ValidatorUtil;
import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final String ORDER_FILE_PATH = "src/main/resources/JsonImport/orderSeed.json";
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String deleteOrder(int id,int userId) {

        Order order = this.orderRepository.findById(id).orElse(null);

        if(order==null) return "No order with such id exist";

        User user = this.userRepository.findById(userId).orElse(null);

        BigDecimal bigDecimal = user.getPayment()
                .getBalance().add((BigDecimal) this.orderRepository.getProductsPriceSum(id)[0]);

        user.getPayment().setBalance(bigDecimal);

        this.userRepository.saveAndFlush(user);

        this.orderRepository.deleteOrder(id);

        return "Successfully deleted";
    }

    @Override
    public String profitAfterDate(LocalDate localDate) {

        BigDecimal profit = this.orderRepository.getProfitAfterDate(localDate);

        if(profit==null) return "No orders for this period of time. ";

        return "Money made after "+ localDate +  " :  "+ profit + "$";
    }

    @Override
    public String getPurchaseOrdersProfit() {

        StringBuilder sb = new StringBuilder();

        OrderSumOrdersAndProductsDto info = this.orderRepository.getSumOrdersAndProducts();

        return sb.append("Purchases: ").append(info.getPurchases()).append(System.lineSeparator())
                .append("Products sold: ").append(info.getProductsSold()).append(System.lineSeparator())
                .append("Total profit: ").append(info.getProfit()).toString();
    }

    @Override
    public String profitBeforeDate(LocalDate localDate) {

        BigDecimal profit = this.orderRepository.getProfitBeforeDate(localDate);

        if(profit==null) return "No orders for this period of time. ";

        return "Money made before "+ localDate +  " :  "+ profit + "$";
    }
}
