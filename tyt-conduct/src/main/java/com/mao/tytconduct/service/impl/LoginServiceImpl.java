package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.model.exception.NotValidUserException;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserResponse userIsValid(String userName, String password) {
        UserEntity entity = userEntityRepository.findByNameAndIsDeletedIsFalse(userName)
                .orElseThrow(() -> new NotFoundException(userName));

        if (entity.getPassword().equals(password)) {
            return UserResponse.entityMappedToResponse(entity);
        }

        throw new NotValidUserException(userName);
    }
}
