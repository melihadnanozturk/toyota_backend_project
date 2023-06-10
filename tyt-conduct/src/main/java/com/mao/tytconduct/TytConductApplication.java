package com.mao.tytconduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TytConductApplication {

    public static void main(String[] args) {
        SpringApplication.run(TytConductApplication.class, args);
    }

}
