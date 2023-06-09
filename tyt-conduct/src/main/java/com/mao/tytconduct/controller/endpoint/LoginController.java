package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @SneakyThrows
    public BaseResponse<UserResponse> userIsValid(@RequestBody UserRequest request) {
        UserResponse response = loginService.userIsValid(request);
        return BaseResponse.isSuccess(response);
    }
}
