package com.app.project.services;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public interface OrderService {

    String deleteOrder(int id,int userId);

    String profitAfterDate(LocalDate localDate);

    String profitBeforeDate(LocalDate localDate);

    String getPurchaseOrdersProfit();
}
