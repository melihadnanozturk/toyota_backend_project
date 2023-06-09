package com.mao.tytauth.controller.endpoint;

import com.mao.tytauth.controller.request.UserRequest;
import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.model.User;
import com.mao.tytauth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping
    public BaseResponse<String> login(@RequestBody UserRequest userRequest) {

        User user = User.builder()
                .userName(userRequest.getName())
                .password(userRequest.getPassword())
                .build();

        String token = tokenService.createToken(new HashMap<>(), user);

        return BaseResponse.isSuccess(token);
    }
}
