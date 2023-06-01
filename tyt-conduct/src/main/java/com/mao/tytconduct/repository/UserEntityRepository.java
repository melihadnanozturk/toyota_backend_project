package com.mao.tytconduct.repository;

import com.mao.tytconduct.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndIsDeletedIsFalse(Long id);

    Optional<UserEntity> findByNameAndIsDeletedIsFalse(String name);
}
