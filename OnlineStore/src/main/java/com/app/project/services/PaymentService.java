package com.app.project.services;

import java.io.IOException;

public interface PaymentService {

    void seedPayments() throws IOException;
    void setLoggedInUser(String email);
    void setLoggedOutUser();
    void printBalanceAndCardType();

}
