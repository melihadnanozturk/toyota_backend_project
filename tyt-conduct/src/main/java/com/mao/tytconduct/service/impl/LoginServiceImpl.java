package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserResponse userIsValid(UserRequest request) throws Exception {
        UserEntity entity = userEntityRepository.findByNameAndIsDeletedIsFalse(request.getName())
                .orElseThrow(() -> new NotFoundException(request.getName()));

        if (entity.getPassword().equals(request.getPassword())) {
            return UserResponse.entityMappedToResponse(entity);
        }

        throw new Exception();
    }
}
