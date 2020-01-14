package com.app.project.services.impl;

import com.app.project.domain.dtos.PaymentDtos.BalanceDto;
import com.app.project.domain.dtos.PaymentDtos.PaymentSeedDto;
import com.app.project.domain.entities.Payment;
import com.app.project.repositories.PaymentRepository;
import com.app.project.services.PaymentService;
import com.app.project.util.FileUtil;
import com.app.project.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final String PAYMENT_FILE_PATH = "src/main/resources/JsonImport/paymentsSeed.json";
    private PaymentRepository paymentRepository;
    private ValidatorUtil validatorUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;
    private String loggedInUser = "";

    @Autowired
    public PaymentServiceImpl(ModelMapper modelMapper, Gson gson, FileUtil fileUtil, PaymentRepository paymentRepository, ValidatorUtil validatorUtil) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.paymentRepository = paymentRepository;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public void seedPayments() throws IOException {

        String information= this.fileUtil.fileContent(PAYMENT_FILE_PATH);

        PaymentSeedDto[] paymentSeedDtos = this.gson.fromJson(information,PaymentSeedDto[].class);

        for(PaymentSeedDto paymentSeedDto: paymentSeedDtos){

            if(!validatorUtil.isValid(paymentSeedDto)){

                validatorUtil.vioations(paymentSeedDto)
                        .forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            Payment payment = this.modelMapper.map(paymentSeedDto,Payment.class);
            this.paymentRepository.saveAndFlush(payment);

        }


    }

    @Override
    public void printBalanceAndCardType() {

        BalanceDto balanceDto = this.paymentRepository.findBalance(this.loggedInUser);
        System.out.println("You have "+ balanceDto.getBalance() +"$ in your "+ balanceDto.getPaymentType() +" balance");

    }

    @Override
    public void setLoggedInUser(String email) {
        this.loggedInUser=email;
    }

    @Override
    public void setLoggedOutUser() {

    }
}
