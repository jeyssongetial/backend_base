package com.lego.backend.usescase;

import com.lego.backend.domain.ex.TechnicalException;
import com.lego.backend.domain.models.Tarea;

import com.lego.backend.infrastruture.adapter.TareasAdapter;
import com.lego.backend.infrastruture.jpa.TaskRepositoryJpa;
import com.lego.backend.infrastruture.jpa.UserRepositoryJpa;
import com.lego.backend.infrastruture.jpa.entity.TaskEntity;
import com.lego.backend.infrastruture.jpa.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareasAdapterTest {

    @Mock
    private TaskRepositoryJpa taskRepositoryJpa;

    @Mock
    private UserRepositoryJpa userRepositoryJpa;

    @InjectMocks
    private TareasAdapter tareasAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardar() {
        // Arrange
        Tarea tarea = Tarea.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .dueDate(new Date())
                .status("PENDING")
                .assignedTo(2L)
                .createdBy(3L)
                .build();

        UserEntity assignedToUser = new UserEntity();
        assignedToUser.setId(2L);

        UserEntity createdByUser = new UserEntity();
        createdByUser.setId(3L);

        TaskEntity savedTaskEntity = TaskEntity.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .dueDate(new Date())
                .status("PENDING")
                .assignedTo(assignedToUser)
                .createdBy(createdByUser)
                .build();

        when(userRepositoryJpa.findById(2L)).thenReturn(Optional.of(assignedToUser));
        when(userRepositoryJpa.findById(3L)).thenReturn(Optional.of(createdByUser));
        when(taskRepositoryJpa.save(any(TaskEntity.class))).thenReturn(savedTaskEntity);

        // Act
        Tarea result = tareasAdapter.guardar(tarea);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals("PENDING", result.getStatus());
        assertEquals(2L, result.getAssignedTo());
        assertEquals(3L, result.getCreatedBy());

        verify(userRepositoryJpa, times(1)).findById(2L);
        verify(userRepositoryJpa, times(1)).findById(3L);
        verify(taskRepositoryJpa, times(1)).save(any(TaskEntity.class));
    }


        @Test
        void testBuscarPorId() {
            // Arrange
            TaskEntity taskEntity = TaskEntity.builder()
                    .id(1L)
                    .title("Test Task")
                    .description("Test Description")
                    .dueDate(new Date())
                    .status("PENDING")
                    .assignedTo(UserEntity.builder().id(2L).build())
                    .createdBy(UserEntity.builder().id(3L).build())
                    .build();

            when(taskRepositoryJpa.findById(1L)).thenReturn(Optional.of(taskEntity));

            // Act
            Tarea result = tareasAdapter.buscarPorId(1L);

            // Assert
            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Test Task", result.getTitle());
            assertEquals("Test Description", result.getDescription());
            assertEquals("PENDING", result.getStatus());
            assertEquals(2L, result.getAssignedTo());
            assertEquals(2L, result.getCreatedBy());
            verify(taskRepositoryJpa, times(1)).findById(1L);
        }

        @Test
        void testBuscarPorId_NotFound() {
            // Arrange
            when(taskRepositoryJpa.findById(1L)).thenReturn(Optional.empty());

            // Act
            Tarea result = tareasAdapter.buscarPorId(1L);

            // Assert
            assertNull(result);
            verify(taskRepositoryJpa, times(1)).findById(1L);
        }

        @Test
        void testListarTareas() {
            // Arrange
            UserEntity userEntity = new UserEntity();
            userEntity.setId(2L);

            TaskEntity taskEntity = TaskEntity.builder()
                    .id(1L)
                    .title("Test Task")
                    .description("Test Description")
                    .dueDate(new Date())
                    .status("PENDING")
                    .assignedTo(userEntity)
                    .createdBy(userEntity)
                    .build();

            when(userRepositoryJpa.findById(2L)).thenReturn(Optional.of(userEntity));
            when(taskRepositoryJpa.findByAssignedTo(userEntity)).thenReturn(List.of(taskEntity));

            // Act
            List<Tarea> result = tareasAdapter.listarTareas(2L);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(1L, result.get(0).getId());
            assertEquals("Test Task", result.get(0).getTitle());

            verify(userRepositoryJpa, times(1)).findById(2L);
            verify(taskRepositoryJpa, times(1)).findByAssignedTo(userEntity);
        }

        @Test
        void testListarTareas_UserNotFound() {
            // Arrange
            when(userRepositoryJpa.findById(2L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(TechnicalException.class, () -> tareasAdapter.listarTareas(2L));
            verify(userRepositoryJpa, times(1)).findById(2L);
            verify(taskRepositoryJpa, never()).findByAssignedTo(any());
        }

        @Test
        void testEliminar() {
            // Act
            tareasAdapter.eliminar(1L);

            // Assert
            verify(taskRepositoryJpa, times(1)).deleteById(1L);
        }
    }
