package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.client.HeaderUtility;
import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import com.mao.tytconduct.model.exception.AlreadyExistsException;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final AuthApiClient apiClient;

    @Override
    public UserResponse addNewUser(HttpHeaders headers, UserRequest request) {
        this.isClientValid(headers);

        this.isUserEntityExistsWithName(request.getName());

        UserEntity entity = UserRequest.mappedToEntity(request);
        UserEntity saved = userEntityRepository.save(entity);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public UserResponse updateUser(HttpHeaders headers, Long id, UserRequest request) {
        this.isClientValid(headers);

        UserEntity entity = this.isUserEntityExists(id);

        entity.setName(request.getName());
        entity.setPassword(request.getPassword());
        entity.setRoles(request.getRoles());

        UserEntity saved = userEntityRepository.save(entity);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public Long removeUser(HttpHeaders headers, Long id) {
        this.isClientValid(headers);

        UserEntity entity = this.isUserEntityExists(id);
        entity.setIsDeleted(true);
        userEntityRepository.save(entity);
        return id;
    }

    private UserEntity isUserEntityExists(Long id) {
        return userEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void isUserEntityExistsWithName(String name) {
        if (userEntityRepository.findByNameAndIsDeletedIsFalse(name).isPresent()) {
            throw new AlreadyExistsException(name);
        }
    }

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);

        apiClient.validate(clientHeaders, Role.ADMIN);
    }
}
