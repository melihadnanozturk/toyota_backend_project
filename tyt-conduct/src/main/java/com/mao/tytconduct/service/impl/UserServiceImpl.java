package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import com.mao.tytconduct.model.exception.AlreadyExistsException;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.model.utility.HeaderUtility;
import com.mao.tytconduct.repository.UserEntityRepository;
import com.mao.tytconduct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserResponse addNewUser(HttpHeaders headers, UserRequest request) {
        String userName = this.isClientValid(headers);

        this.isUserEntityExistsWithName(request.getName());

        UserEntity entity = UserRequest.mappedToEntity(request);
        entity.setCreatedBy(userName);

        UserEntity saved = userEntityRepository.save(entity);
        logger.atInfo().log("{} record was saved", saved.getName());

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public UserResponse updateUser(HttpHeaders headers, Long id, UserRequest request) {
        String userName = this.isClientValid(headers);

        UserEntity entity = this.isUserEntityExists(id);
        String updatedName = entity.getName();

        entity.setName(request.getName());
        entity.setPassword(request.getPassword());
        entity.setRoles(request.getRoles());
        entity.setUpdatedBy(userName);

        UserEntity saved = userEntityRepository.save(entity);
        logger.atInfo().log("{} was updated", updatedName);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public Long removeUser(HttpHeaders headers, Long id) {
        String userName = this.isClientValid(headers);

        UserEntity entity = this.isUserEntityExists(id);
        entity.setIsDeleted(true);
        entity.setUpdatedBy(userName);

        userEntityRepository.save(entity);
        logger.atInfo().log("{} was removed", entity.getName());

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

    private String isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = Objects.requireNonNull(clientHeaders.get("userName")).get(0);

        logger.atInfo().log("{} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.ADMIN);
        return userName;
    }
}
