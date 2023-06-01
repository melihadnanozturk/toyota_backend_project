package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    BaseResponse<UserResponse> addNewUser(@RequestBody UserRequest request) {
        UserResponse response = userService.addNewUser(request);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    BaseResponse<UserResponse> updateUser(@RequestBody UserRequest request, @PathVariable Long id) {
        UserResponse response = userService.updateUser(id, request);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    BaseResponse<Long> deleteUser(@PathVariable Long id) {
        Long removed = userService.removeUser(id);
        return BaseResponse.isSuccess(removed);
    }
}
