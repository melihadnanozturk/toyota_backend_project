package com.mao.tytauth.client;

import com.mao.tytauth.controller.response.BaseResponse;
import com.mao.tytauth.controller.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * This feign-client is used to send request to conduct service
 */
@FeignClient(value = "CONDUCT", url = "${tyt.32Bit.feign-client.conduct}")
public interface ConductApiClient {


    /**
     * Send request for check user information
     *
     * @param headers has UserName, Password
     * @return UserResponse
     */
    @PostMapping("/check")
    BaseResponse<UserResponse> userIsValid(@RequestHeader HttpHeaders headers);

}
