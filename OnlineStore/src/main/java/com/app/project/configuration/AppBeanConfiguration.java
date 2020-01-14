package com.app.project.configuration;

import com.app.project.util.FileUtil;
import com.app.project.util.FileUtilImpl;
import com.app.project.util.ValidatorUtil;
import com.app.project.util.ValidatorUtilImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;


@Configuration
public class AppBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(this.validator());
    }

    @Bean
    public FileUtil fileUtil(){
        return new FileUtilImpl();
    }

}
