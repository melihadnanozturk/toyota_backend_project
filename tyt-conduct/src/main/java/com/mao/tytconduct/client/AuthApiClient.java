package com.mao.tytconduct.client;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.model.entity.enums.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "AUTH", url = "${tyt.32Bit.feign-client.auth}")
public interface AuthApiClient {

    @PostMapping("/auth/validate")
    BaseResponse<Boolean> validate(@RequestHeader HttpHeaders headers,
                                   @RequestBody Role role);

    @PostMapping("/validate-user")
    BaseResponse<Boolean> auth(@RequestHeader HttpHeaders headers);
}
