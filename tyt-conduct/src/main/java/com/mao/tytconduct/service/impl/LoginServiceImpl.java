package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.model.exception.auth.InvalidLoginRequestException;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserResponse userIsValid(HttpHeaders headers) {
        String userName = getUserName(headers);
        String password = getPassword(headers);

        UserEntity entity = userEntityRepository.findByNameAndIsDeletedIsFalse(userName)
                .orElseThrow(() -> new NotFoundException(userName));

        if (entity.getPassword().equals(password)) {
            return UserResponse.entityMappedToResponse(entity);
        }

        throw new InvalidLoginRequestException();
    }

    private String getUserName(HttpHeaders headers) {
        return Objects.requireNonNull(headers.get("userName")).get(0);
    }

    private String getPassword(HttpHeaders headers) {
        return Objects.requireNonNull(headers.get("password")).get(0);
    }
}
