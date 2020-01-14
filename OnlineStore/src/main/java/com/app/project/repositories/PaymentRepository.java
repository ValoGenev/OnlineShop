package com.app.project.repositories;

import com.app.project.domain.dtos.PaymentDtos.BalanceDto;
import com.app.project.domain.entities.Payment;
import com.app.project.domain.entities.PaymentType;
import com.app.project.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query("select new com.app.project.domain.dtos.PaymentDtos.BalanceDto(p.balance,p.paymentType) " +
            "from Payment as p " +
            "join p.user as u " +
            "where u.email like :email")
    BalanceDto findBalance(String email);


}
