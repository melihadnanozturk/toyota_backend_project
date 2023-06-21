package com.mao.tytconduct.repository;

import com.mao.tytconduct.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This repository interface for CRUD operations on the UserEntityRepository objects in the database.
 */
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Retrieves non-deleted UserEntity about to id
     *
     * @param id non-deleted UserEntity id
     * @return UserEntity found about to id
     */
    Optional<UserEntity> findByIdAndIsDeletedIsFalse(Long id);

    /**
     * Retrieves non-deleted UserEntity about to name
     *
     * @param name non-deleted UserEntity name
     * @return UserEntity found about to id
     */
    Optional<UserEntity> findByNameAndIsDeletedIsFalse(String name);
}
