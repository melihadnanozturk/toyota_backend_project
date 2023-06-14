package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.client.AuthApiClient;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final AuthApiClient apiClient;

    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";


    @Override
    public UserResponse addNewUser(HttpHeaders headers, UserRequest request) {
        this.isValidRequest(headers);

        this.isUserEntityExistsWithName(request.getName());

        UserEntity entity = UserRequest.mappedToEntity(request);
        UserEntity saved = userEntityRepository.save(entity);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public UserResponse updateUser(HttpHeaders headers, Long id, UserRequest request) {
        this.isValidRequest(headers);

        UserEntity entity = this.isUserEntityExists(id);

        entity.setName(request.getName());
        entity.setPassword(request.getPassword());
        entity.setRoles(request.getRoles());

        UserEntity saved = userEntityRepository.save(entity);

        return UserResponse.entityMappedToResponse(saved);
    }

    @Override
    public Long removeUser(HttpHeaders headers, Long id) {
        this.isValidRequest(headers);

        UserEntity entity = this.isUserEntityExists(id);
        entity.setIsDeleted(true);
        userEntityRepository.save(entity);
        return id;
    }

    private void isValidRequest(HttpHeaders headers) {
        Map<String, String> info = getHeaderInfo(headers);

        apiClient.validate(info.get(USER_NAME), info.get(TOKEN), Role.ADMIN);
    }

    private Map<String, String> getHeaderInfo(HttpHeaders headers) {
        Map<String, String> infos = new HashMap<>();

        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);

        infos.put(USER_NAME, userName);
        infos.put(TOKEN, token);

        return infos;
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
}
