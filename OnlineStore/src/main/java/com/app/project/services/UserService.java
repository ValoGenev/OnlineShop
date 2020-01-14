package com.app.project.services;

import com.app.project.domain.dtos.UserDtos.*;
import com.app.project.domain.entities.Gender;
import com.app.project.domain.entities.PaymentType;
import com.app.project.domain.entities.User;
import com.app.project.exceptions.UserNotFoundException;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    <E> void toGson(String path, Class<E> type) throws IOException;

    void seedUsers() throws IOException;

    boolean loginUser(UserLoginDto userLoginDto);

    String purchaseProduct(int... ids);

    String getShoppingCart(int... id);

    String checkMail();

    boolean checkIfAdmin();

    List<User> returnAllUser();

    List<User> returnAllUserWithGender(Gender gender);

    void readMessage();

    String addBalanceToUser(int id, BigDecimal bigDecimal);

    List<User> findAllBySpecificPaymentType(String... paymentType);

    User getLoggedInUser();

    List<UserPaymentsCountDto> findTopByBalance();

    List<UserWithMostSpendedMoneyDto> findUserWithMostSpendedMoney();

    void sendMessage(String message);

    String changePassword(String oldPassword, String newPassword);

    String responseTicket(int id,String response);

    List<User> getAllUsers();

    User getUser(String email) throws UserNotFoundException;
}
