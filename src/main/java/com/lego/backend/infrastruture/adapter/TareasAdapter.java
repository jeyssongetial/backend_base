package com.lego.backend.infrastruture.adapter;

import com.lego.backend.domain.ex.TechnicalException;
import com.lego.backend.domain.models.Tarea;
import com.lego.backend.domain.repository.TareasRepository;
import com.lego.backend.infrastruture.jpa.TaskRepositoryJpa;
import com.lego.backend.infrastruture.jpa.UserRepositoryJpa;
import com.lego.backend.infrastruture.jpa.entity.TaskEntity;
import com.lego.backend.infrastruture.jpa.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TareasAdapter implements TareasRepository {

    private final TaskRepositoryJpa taskRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;

    @Override
    public Tarea guardar(Tarea tarea) {

        UserEntity userEntity = userRepositoryJpa.findById(tarea.getAssignedTo())
                .orElseThrow(TechnicalException.Type.ERROR_USUARIO_NO_ENCONTRADO::build);

        UserEntity creatorEntity = userRepositoryJpa.findById(tarea.getCreatedBy())
                .orElseThrow(TechnicalException.Type.ERROR_USUARIO_NO_ENCONTRADO::build);

        TaskEntity taskEntity = TaskEntity.builder()
                .id(tarea.getId())
                .title(tarea.getTitle())
                .description(tarea.getDescription())
                .dueDate(tarea.getDueDate())
                .status(tarea.getStatus())
                .assignedTo(userEntity)
                .createdBy(creatorEntity)
                .build();
        TaskEntity savedEntity = taskRepositoryJpa.save(taskEntity);
        return Tarea.builder()
                .id(savedEntity.getId())
                .title(savedEntity.getTitle())
                .description(savedEntity.getDescription())
                .dueDate(savedEntity.getDueDate())
                .status(savedEntity.getStatus())
                .assignedTo(tarea.getAssignedTo())
                .createdBy(tarea.getCreatedBy())
                .build();
    }

    @Override
    public Tarea buscarPorId(Long id) {
        Optional<TaskEntity> taskEntityOptional = taskRepositoryJpa.findById(id);
        if (taskEntityOptional.isPresent()) {
            TaskEntity taskEntity = taskEntityOptional.get();
            return Tarea.builder()
                    .id(taskEntity.getId())
                    .title(taskEntity.getTitle())
                    .description(taskEntity.getDescription())
                    .dueDate(taskEntity.getDueDate())
                    .status(taskEntity.getStatus())
                    .assignedTo(taskEntity.getAssignedTo().getId())
                    .createdBy(taskEntity.getAssignedTo().getId())
                    .build();
        }
        return null;
    }

    @Override
    public List<Tarea> listarTareas(Long userId) {

        UserEntity userEntity = userRepositoryJpa.findById(userId)
                .orElseThrow(TechnicalException.Type.ERROR_USUARIO_NO_ENCONTRADO::build);
        List<TaskEntity> taskEntities = taskRepositoryJpa.findByAssignedTo(userEntity);
        return taskEntities.stream()
                .map(taskEntity -> Tarea.builder()
                        .id(taskEntity.getId())
                        .title(taskEntity.getTitle())
                        .description(taskEntity.getDescription())
                        .dueDate(taskEntity.getDueDate())
                        .status(taskEntity.getStatus())
                        .assignedTo(userId)
                        .createdBy(userId)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        taskRepositoryJpa.deleteById(id);
    }
}
