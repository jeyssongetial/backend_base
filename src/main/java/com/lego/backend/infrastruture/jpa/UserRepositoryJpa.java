package com.lego.backend.infrastruture.jpa;

import com.lego.backend.infrastruture.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<UserEntity,Long> {
}
