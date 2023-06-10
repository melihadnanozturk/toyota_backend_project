package com.mao.tytauth.controller.endpoint;

import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    //alınan tokenı kontrol etmek için
    @PostMapping("/validate-user")
    public BaseResponse<Boolean> auth(@RequestHeader("userName") String userName,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        return BaseResponse.isSuccess(tokenService.authentication(token, userName));
    }

    //ilk başta token almak için
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestHeader("userName") String userName,
                                      @RequestHeader("password") String password) {

        String token = tokenService.createToken(userName, password);

        return BaseResponse.isSuccess(token);
    }

    //diğer servicelerden gelen tokenı doğrulamak için
    @PostMapping("/validate")
    public BaseResponse<Boolean> validate(@RequestHeader("userName") String userName,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                          @RequestBody Role role) {

        Boolean isValid = tokenService.authorization(userName, token, role);

        return BaseResponse.isSuccess(isValid);
    }
}
