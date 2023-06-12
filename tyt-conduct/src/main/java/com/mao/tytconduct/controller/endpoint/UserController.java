package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    BaseResponse<UserResponse> addNewUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestHeader("userName") String userName,
            @RequestBody @Valid UserRequest request) {

        UserResponse response = userService.addNewUser(userName, token, request);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    BaseResponse<UserResponse> updateUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestHeader("userName") String userName,
            @RequestBody @Valid UserRequest request,
            @PathVariable Long id) {

        UserResponse response = userService.updateUser(userName, token, id, request);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    BaseResponse<Long> deleteUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestHeader("userName") String userName,
            @PathVariable Long id) {

        Long removed = userService.removeUser(userName, token, id);
        return BaseResponse.isSuccess(removed);
    }
}
