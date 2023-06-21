package com.mao.tytconduct.repository;

import com.mao.tytconduct.model.entity.TerminalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository interface for CRUD operations on the TerminalEntity objects in the database.
 */
public interface TerminalEntityRepository extends JpaRepository<TerminalEntity, Long> {
}
