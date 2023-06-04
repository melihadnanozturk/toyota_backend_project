package com.mao.tytauth.controller.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TokenController {

    @GetMapping("/login")
    public String trying() {
        return "Deneme Basarili";
    }
}
