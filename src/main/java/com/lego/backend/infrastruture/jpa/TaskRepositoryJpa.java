package com.lego.backend.infrastruture.jpa;

import com.lego.backend.infrastruture.jpa.entity.TaskEntity;
import com.lego.backend.infrastruture.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepositoryJpa extends JpaRepository <TaskEntity , Long> {
    List<TaskEntity> findByAssignedTo(UserEntity userId);
}
