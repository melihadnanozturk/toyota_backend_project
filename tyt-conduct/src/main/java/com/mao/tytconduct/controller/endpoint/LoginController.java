package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller is used to send request for Login process
 */
@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * Provides valid user information
     *
     * @param headers contains UserName, Password
     * @return BaseResponse containing valid user information
     */
    @PostMapping
    public BaseResponse<UserResponse> checkUser(
            @RequestHeader HttpHeaders headers) {
        UserResponse response = loginService.isUserValid(headers);
        return BaseResponse.isSuccess(response);
    }
}
