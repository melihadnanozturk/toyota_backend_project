package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserResponse addNewUser(UserRequest request) {
        UserEntity entity = UserRequest.mappedToEntity(request);
        UserEntity saved = userEntityRepository.save(entity);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        UserEntity entity = this.userEntityIsExists(id);

        entity.setName(request.getName());
        entity.setPassword(request.getPassword());
        entity.setRoles(request.getRoles());

        UserEntity saved = userEntityRepository.save(entity);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public Long removeUser(Long id) {
        UserEntity entity = this.userEntityIsExists(id);
        entity.setIsDeleted(true);
        return id;
    }

    private UserEntity userEntityIsExists(Long id) {
        return userEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }
}
