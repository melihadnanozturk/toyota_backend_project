package com.mao.tytmistake.client.request;

import com.mao.tytmistake.model.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValidateRequest {

    private String user;
    private String token;
    private Role role;

}
