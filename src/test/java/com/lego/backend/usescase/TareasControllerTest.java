package com.lego.backend.usescase;

import com.lego.backend.domain.models.Tarea;
import com.lego.backend.domain.usecases.TareasUseCase;
import com.lego.backend.infrastruture.controller.TareasController;
import com.lego.backend.infrastruture.dto.TareaDto;
import com.lego.backend.infrastruture.dto.UpdateTareaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareasControllerTest {

    @Mock
    private TareasUseCase tareasUseCase;

    @InjectMocks
    private TareasController tareasController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearTarea() {
        // Arrange
        TareaDto tareaDto = TareaDto.builder()
                .title("Test Task")
                .description("Test Description")
                .dueDate(new Date())
                .status("PENDING")
                .assignedTo(2L)
                .createdBy(3L)
                .build();

        Tarea tarea = tareaDto.toDomain();
        when(tareasUseCase.crearTarea(any(Tarea.class))).thenReturn(tarea);

        // Act
        ResponseEntity<Tarea> response = tareasController.crearTarea(tareaDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tarea, response.getBody());
        verify(tareasUseCase, times(1)).crearTarea(any(Tarea.class));
    }

    @Test
    void testListarTareas() {
        // Arrange
        Long userId = 1L;
        List<Tarea> tareas = Arrays.asList(
                Tarea.builder().id(1L).title("Task 1").build(),
                Tarea.builder().id(2L).title("Task 2").build()
        );
        when(tareasUseCase.listarTareas(userId)).thenReturn(tareas);

        // Act
        ResponseEntity<List<Tarea>> response = tareasController.listarTareas(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tareas, response.getBody());
        verify(tareasUseCase, times(1)).listarTareas(userId);
    }

    @Test
    void testObtenerTareaPorId() {
        // Arrange
        Long tareaId = 1L;
        Tarea tarea = Tarea.builder().id(tareaId).title("Test Task").build();
        when(tareasUseCase.obtenerTareaPorId(tareaId)).thenReturn(tarea);

        // Act
        ResponseEntity<Tarea> response = tareasController.obtenerTareaPorId(tareaId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tarea, response.getBody());
        verify(tareasUseCase, times(1)).obtenerTareaPorId(tareaId);
    }

    @Test
    void testObtenerTareaPorId_NotFound() {
        // Arrange
        Long tareaId = 1L;
        when(tareasUseCase.obtenerTareaPorId(tareaId)).thenReturn(null);

        // Act
        ResponseEntity<Tarea> response = tareasController.obtenerTareaPorId(tareaId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(tareasUseCase, times(1)).obtenerTareaPorId(tareaId);
    }

    @Test
    void testActualizarTarea() {
        // Arrange
        Long tareaId = 1L;
        UpdateTareaDto updateTareaDto = UpdateTareaDto.builder()
                .title("Updated Task")
                .description("Updated Description")
                .status("COMPLETED")
                .build();

        Tarea updatedTarea = updateTareaDto.toDomain();
        updatedTarea.setId(tareaId);
        when(tareasUseCase.actualizarTarea(eq(tareaId), any(Tarea.class))).thenReturn(updatedTarea);

        // Act
        ResponseEntity<Tarea> response = tareasController.actualizarTarea(tareaId, updateTareaDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedTarea, response.getBody());
        verify(tareasUseCase, times(1)).actualizarTarea(eq(tareaId), any(Tarea.class));
    }

    @Test
    void testActualizarTarea_NotFound() {
        // Arrange
        Long tareaId = 1L;
        UpdateTareaDto updateTareaDto = UpdateTareaDto.builder()
                .title("Updated Task")
                .description("Updated Description")
                .status("COMPLETED")
                .build();

        when(tareasUseCase.actualizarTarea(eq(tareaId), any(Tarea.class))).thenReturn(null);

        // Act
        ResponseEntity<Tarea> response = tareasController.actualizarTarea(tareaId, updateTareaDto);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(tareasUseCase, times(1)).actualizarTarea(eq(tareaId), any(Tarea.class));
    }

    @Test
    void testEliminarTarea() {
        // Arrange
        Long tareaId = 1L;
        when(tareasUseCase.eliminarTarea(tareaId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = tareasController.eliminarTarea(tareaId);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(tareasUseCase, times(1)).eliminarTarea(tareaId);
    }

    @Test
    void testEliminarTarea_NotFound() {
        // Arrange
        Long tareaId = 1L;
        when(tareasUseCase.eliminarTarea(tareaId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = tareasController.eliminarTarea(tareaId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(tareasUseCase, times(1)).eliminarTarea(tareaId);
    }
}