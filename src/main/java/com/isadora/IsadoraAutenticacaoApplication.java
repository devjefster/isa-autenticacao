package com.isadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class IsadoraAutenticacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsadoraAutenticacaoApplication.class, args);
    }




}
