package com.mao.tytconduct.client;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.model.enums.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * This feign-client is used to send request to Auth service
 */
@FeignClient(value = "AUTH", url = "${tyt.32Bit.feign-client.auth}")
public interface AuthApiClient {

    /**
     * Send request for authentication
     *
     * @param headers UserName, Password
     * @param role    Required permission
     * @return Boolean that the client has valid permission or not
     */
    @PostMapping("/auth/validate")
    BaseResponse<Boolean> validate(@RequestHeader HttpHeaders headers,
                                   @RequestBody Role role);

    /**
     * Send request for authorization
     *
     * @param headers UserName, JWT Token
     * @return Boolean that the client is valid or not
     */
    @PostMapping("/auth/validate-user")
    BaseResponse<Boolean> auth(@RequestHeader HttpHeaders headers);
}
