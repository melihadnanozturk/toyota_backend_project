package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public BaseResponse<UserResponse> checkUser(@RequestHeader("userName") String userName, @RequestHeader("password") String password) {
        UserResponse response = loginService.userIsValid(userName, password);
        return BaseResponse.isSuccess(response);
    }
}
