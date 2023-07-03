package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.BaseUnitTest;
import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.request.UserRequestBuilder;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.UserEntityBuilder;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.enums.Role;
import com.mao.tytconduct.model.exception.AlreadyExistsException;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest extends BaseUnitTest {

    @Mock
    private AuthApiClient apiClient;
    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void addNewUser_existUserName_throwAlreadyException() {
        HttpHeaders testHeaders = createHeader();
        String testName = "testName";

        UserEntity testUserEntity = new UserEntityBuilder(true)
                .withName(testName).build();
        UserRequest userRequest = new UserRequestBuilder()
                .withName(testName).build();

        when(userEntityRepository.findByNameAndIsDeletedIsFalse(testName)).thenReturn(Optional.of(testUserEntity));

        Assertions.assertThrows(AlreadyExistsException.class, () -> userService.addNewUser(testHeaders, userRequest));
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
        verify(userEntityRepository, times(1)).findByNameAndIsDeletedIsFalse(anyString());
    }

    @Test
    void addUser_happyPath() {
        HttpHeaders testHeaders = createHeader();
        String testName = "testName";
        UserRequest testRequest = new UserRequestBuilder().withName(testName).build();
        UserEntity testEntity = new UserEntityBuilder(true).withName(testName).build();
        UserResponse expected = UserResponse.entityMappedToResponse(testEntity);

        when(userEntityRepository.findByNameAndIsDeletedIsFalse(testName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(testEntity);

        UserResponse userResponse = userService.addNewUser(testHeaders, testRequest);

        Assertions.assertEquals(expected, userResponse);
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
        verify(userEntityRepository, times(1)).findByNameAndIsDeletedIsFalse(anyString());
        verify(userEntityRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUser_notExistsUserId_throwNotFoundException() {
        Long testId = 1L;
        HttpHeaders testHeaders = createHeader();
        UserRequest testUserRequest = new UserRequestBuilder().build();

        when(userEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.updateUser(testHeaders, testId, testUserRequest));
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
        verify(userEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
    }

    @Test
    void updateUser_happyPath() {
        Long testId = 1L;
        HttpHeaders testHeaders = createHeader();

        UserRequest testUserRequest = new UserRequestBuilder().build();
        UserEntity testUserEntity = new UserEntityBuilder(true).withId(testId).build();
        UserResponse expected = UserResponse.entityMappedToResponse(testUserEntity);

        when(userEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.of(testUserEntity));
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(testUserEntity);

        UserResponse response = userService.updateUser(testHeaders, testId, testUserRequest);

        Assertions.assertEquals(expected, response);
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
        verify(userEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
    }

    @Test
    void removeUser_notExistsUserId_throwNotFoundException() {
        Long testId = 1L;
        HttpHeaders testHeaders = createHeader();

        when(userEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.removeUser(testHeaders, testId));
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
        verify(userEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
    }

    @Test
    void removeUser_happyPath() {
        Long testId = 1L;
        HttpHeaders testHeaders = createHeader();
        UserEntity testEntity = new UserEntityBuilder(true).withId(testId).build();

        when(userEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.ofNullable(testEntity));

        Long response = userService.removeUser(testHeaders, testId);

        Assertions.assertEquals(testId, response);
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
        verify(userEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}