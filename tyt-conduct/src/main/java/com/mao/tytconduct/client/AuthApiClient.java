package com.mao.tytconduct.client;

import com.mao.tytconduct.client.request.ValidateRequest;
import com.mao.tytconduct.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "AUTH", url = "${tyt.32Bit.feign-client.auth}")
public interface AuthApiClient {

    @PostMapping("/auth/validate")
    BaseResponse<Boolean> validate(@RequestBody ValidateRequest request);
}
