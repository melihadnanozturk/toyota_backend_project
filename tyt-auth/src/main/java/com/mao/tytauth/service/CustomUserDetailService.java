package com.mao.tytauth.service;

import com.mao.tytauth.controller.response.CustomUserDetails;
import com.mao.tytauth.model.entity.UserEntity;
import com.mao.tytauth.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserEntityRepository userRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByNameAndIsDeletedIsFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not founded: " + username));

        return new CustomUserDetails(user);
    }
}
