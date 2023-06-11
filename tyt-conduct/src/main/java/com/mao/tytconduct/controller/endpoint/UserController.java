package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.enums.Role;
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
    private final AuthApiClient apiClient;

    @PostMapping
    BaseResponse<UserResponse> addNewUser(
            @RequestHeader("userName") String userName,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody @Valid UserRequest request) {

        //todo: bu bir yerde ortaklaştırılabilir
        /*ValidateRequest validateRequest = ValidateRequest.builder()
                .user(userName)
                .role(Role.ADMIN)
                .token(token)
                .build();*/

        BaseResponse<Boolean> feignResponse = apiClient.validate(userName, token, Role.ADMIN);

        UserResponse response = userService.addNewUser(request);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    BaseResponse<UserResponse> updateUser(@RequestBody @Valid UserRequest request, @PathVariable Long id) {
        UserResponse response = userService.updateUser(id, request);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    BaseResponse<Long> deleteUser(@PathVariable Long id) {
        Long removed = userService.removeUser(id);
        return BaseResponse.isSuccess(removed);
    }
}
