package com.mao.tytauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TytAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TytAuthApplication.class, args);
    }

}
