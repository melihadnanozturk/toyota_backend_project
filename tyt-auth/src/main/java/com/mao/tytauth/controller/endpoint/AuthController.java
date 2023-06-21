package com.mao.tytauth.controller.endpoint;

import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * This Controller is used to send request for Auth process
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    /**
     * Checks if the user is correct
     *
     * @param headers UserName, Bearer Token
     * @return BaseResponse with a boolean for whether it is valid
     */
    @PostMapping("/validate-user")
    public BaseResponse<Boolean> auth(@RequestHeader HttpHeaders headers) {

        return BaseResponse.isSuccess(tokenService.authentication(headers));
    }

    /**
     * Allows admin to take token
     *
     * @param headers contains username and password
     * @return A BaseResponse with a token
     */
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestHeader HttpHeaders headers) {

        String token = tokenService.createToken(headers);

        return BaseResponse.isSuccess(token);
    }

    /**
     * Checks requests from other services for authorization
     *
     * @param headers contains UserName, Bearer token
     * @return BaseResponse with a boolean for whether it is valid
     */
    @PostMapping("/validate")
    public BaseResponse<Boolean> validate(@RequestHeader HttpHeaders headers,
                                          @RequestBody Role role) {

        Boolean isValid = tokenService.authorization(headers, role);

        return BaseResponse.isSuccess(isValid);
    }
}
