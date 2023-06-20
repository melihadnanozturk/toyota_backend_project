package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.BaseUnitTest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.model.UserEntityBuilder;
import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.exception.NotFoundException;
import com.mao.tytconduct.model.exception.auth.InvalidLoginRequestException;
import com.mao.tytconduct.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginServiceImplTest extends BaseUnitTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    void isUserValid_notExistsUserName_throwNotFoundException() {
        String testName = "invalidName";
        String testPassword = "testPassword";
        HttpHeaders testHeaders = createHeader(testName, testPassword);

        when(userEntityRepository.findByNameAndIsDeletedIsFalse(testName))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> loginService.isUserValid(testHeaders));
        verify(userEntityRepository, times(1)).findByNameAndIsDeletedIsFalse(anyString());
    }

    @Test
    void isUserValid_notValidPassword_throwInvalidLoginRequestException() {
        String testName = "testName";
        String testPassword = "invalidPassword";
        HttpHeaders testHeaders = createHeader(testName, testPassword);
        UserEntity testEntity = new UserEntityBuilder(true)
                .withPassword("testPassword").build();

        when(userEntityRepository.findByNameAndIsDeletedIsFalse(testName))
                .thenReturn(Optional.of(testEntity));

        Assertions.assertThrows(InvalidLoginRequestException.class, () -> loginService.isUserValid(testHeaders));
        verify(userEntityRepository, times(1)).findByNameAndIsDeletedIsFalse(anyString());
    }

    @Test
    void isUserValid_happyPath() {
        String testName = "testName";
        String testPassword = "testPassword";
        HttpHeaders testHeaders = createHeader(testName, testPassword);
        UserEntity testEntity = new UserEntityBuilder(true)
                .withName(testName)
                .withPassword(testPassword).build();
        UserResponse expected = UserResponse.entityMappedToResponse(testEntity);

        when(userEntityRepository.findByNameAndIsDeletedIsFalse(testName))
                .thenReturn(Optional.of(testEntity));

        UserResponse response = loginService.isUserValid(testHeaders);

        Assertions.assertEquals(expected, response);
        verify(userEntityRepository, times(1)).findByNameAndIsDeletedIsFalse(anyString());
    }

    private HttpHeaders createHeader(String userName, String password) {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", userName);
        newHeaders.set("password", password);

        return newHeaders;
    }
}