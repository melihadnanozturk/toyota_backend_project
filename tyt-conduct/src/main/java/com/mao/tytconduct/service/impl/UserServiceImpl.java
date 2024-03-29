package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.enums.Role;
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

/**
 * Implementation of the UserService interface that handles user functionality.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    /**
     * This method create new user when client is valid.
     *
     * @param headers Username, Password
     * @param request Contains new user information
     * @throws AlreadyExistsException if user that has name already exists
     */
    @Override
    public UserResponse addNewUser(HttpHeaders headers, UserRequest request) {
        String userName = this.isClientValid(headers);

        this.isUserEntityExistsWithName(request.getName());

        UserEntity entity = UserRequest.mappedToEntity(request);
        entity.setCreatedBy(userName);

        UserEntity saved = userEntityRepository.save(entity);
        logger.atInfo().log("New User with Name {} has been registered ", saved.getName());

        return UserResponse.entityMappedToResponse(saved);
    }

    /**
     * This method update exists user when client is valid.
     *
     * @param headers Username, Password
     * @param request Contains user new information
     * @throws NotFoundException if user that has id not exists
     */
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
        logger.atInfo().log("User with Name {} has been updated ", updatedName);

        return UserResponse.entityMappedToResponse(saved);
    }

    /**
     * This method remove exists user when client is valid.
     *
     * @param headers Username, Password
     * @param id      User that will remove id
     * @throws NotFoundException if user that has id not exists
     */
    @Override
    public Long removeUser(HttpHeaders headers, Long id) {
        String userName = this.isClientValid(headers);

        UserEntity entity = this.isUserEntityExists(id);
        entity.setIsDeleted(true);
        entity.setUpdatedBy(userName);

        userEntityRepository.save(entity);
        logger.atInfo().log("User with Name {} has been removed ", entity.getName());

        return id;
    }

    /**
     * Retrieves the user entity with the specified ID if it exists and is not deleted.
     *
     * @param id Id of user entity to retrieve
     * @return user entity
     * @throws NotFoundException if user that has id not exits
     */
    private UserEntity isUserEntityExists(Long id) {
        return userEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }

    /**
     * Checks if a user entity with the specified name already exists and is not deleted.
     *
     * @param name name of the user entity to check
     * @throws AlreadyExistsException if a user entity that has name already exists
     */
    private void isUserEntityExistsWithName(String name) {
        if (userEntityRepository.findByNameAndIsDeletedIsFalse(name).isPresent()) {
            throw new AlreadyExistsException(name);
        }
    }

    /**
     * Validates the client's authorization and authentication.
     *
     * @param headers HTTP headers containing the necessary information for validation
     */
    private String isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = Objects.requireNonNull(clientHeaders.get("userName")).get(0);

        logger.atInfo().log("User with Name {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.ADMIN);
        return userName;
    }
}
