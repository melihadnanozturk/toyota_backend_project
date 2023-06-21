package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.model.exception.auth.InvalidLoginRequestException;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of the LoginService interface that handles user login functionality.
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    private final UserEntityRepository userEntityRepository;

    /**
     * This method checks if the given information is valid.
     *
     * @param headers - Username, Password
     */
    @Override
    public UserResponse isUserValid(HttpHeaders headers) {
        String userName = getUserName(headers);
        String password = getPassword(headers);

        UserEntity entity = userEntityRepository.findByNameAndIsDeletedIsFalse(userName)
                .orElseThrow(() -> new NotFoundException(userName));

        if (entity.getPassword().equals(password)) {
            logger.atInfo().log(userName + " was login on the System");
            return UserResponse.entityMappedToResponse(entity);
        }

        throw new InvalidLoginRequestException();
    }

    /**
     * Retrieves userName from headers
     *
     * @param headers - Username, Password
     */
    private String getUserName(HttpHeaders headers) {
        return Objects.requireNonNull(headers.get("userName")).get(0);
    }

    /**
     * Retrieves password from headers
     *
     * @param headers - Username, Password
     */
    private String getPassword(HttpHeaders headers) {
        return Objects.requireNonNull(headers.get("password")).get(0);
    }
}
