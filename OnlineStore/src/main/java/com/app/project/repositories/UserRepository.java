package com.app.project.repositories;

import com.app.project.domain.dtos.PaymentDtos.PaymentSeedDto;
import com.app.project.domain.dtos.UserDtos.UserPaymentsCountDto;
import com.app.project.domain.dtos.UserDtos.UserWithMostSpendedMoneyDto;
import com.app.project.domain.entities.Gender;
import com.app.project.domain.entities.Payment;
import com.app.project.domain.entities.PaymentType;
import com.app.project.domain.entities.User;
import org.hibernate.validator.internal.xml.binding.ParameterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findFirstByEmail(String email);

    List<User> findAllByGenderLike(Gender gender);

    User findTopByOrderByPayment_BalanceDesc();


    @Query("select new com.app.project.domain.dtos.UserDtos.UserPaymentsCountDto(u.firstName,u.lastName,count(o)) " +
            "from User as u " +
            "inner join u.orders as o " +
            "group by u " +
            "order by count(o) desc ")
    List<UserPaymentsCountDto> findTopByOrdersCount();


    @Query("select new com.app.project.domain.dtos.UserDtos.UserWithMostSpendedMoneyDto(u.firstName,u.lastName,sum(p.price)) " +
            "from User as u " +
            "inner join u.orders as o " +
            "inner join o.products as p " +
            "group by u " +
            "order by p.price desc ")
    List<UserWithMostSpendedMoneyDto> findUserWithMostSpendedMoney();


    User getByEmailLike(String email);


    List<User> findAllByPayment_PaymentTypeIn(String[] paymentTypes);

    List<User> findAllByPaymentPaymentTypeIn(List<PaymentType> paymentTypes);
}
