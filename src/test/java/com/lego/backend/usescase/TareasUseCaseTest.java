package com.lego.backend.usescase;

import com.lego.backend.domain.ex.BusinessException;
import com.lego.backend.domain.models.Tarea;
import com.lego.backend.domain.repository.TareasRepository;
import com.lego.backend.domain.usecases.TareasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareasUseCaseTest {

    private TareasRepository tareasRepository;
    private TareasUseCase tareasUseCase;

    @BeforeEach
    void setUp() {
        tareasRepository = mock(TareasRepository.class);
        tareasUseCase = new TareasUseCase(tareasRepository);
    }

    @Test
    void crearTarea_ShouldSaveAndReturnTarea() {
        Tarea tarea = Tarea.builder()
                .id(1L)
                .title("Test Tarea")
                .description("This is a test tarea")
                .dueDate(new Date())
                .status("Pending")
                .build();
        when(tareasRepository.guardar(tarea)).thenReturn(tarea);

        Tarea result = tareasUseCase.crearTarea(tarea);

        assertNotNull(result);
        assertEquals(tarea, result);
        verify(tareasRepository, times(1)).guardar(tarea);
    }

    @Test
    void listarTareas_ShouldReturnListOfTareas() {
        List<Tarea> tareas = List.of( Tarea.builder()
                .id(1L)
                .title("Test Tarea")
                .description("This is a test tarea")
                .dueDate(new Date())
                .status("Pending")
                .build());
        when(tareasRepository.listarTareas(1L)).thenReturn(tareas);

        List<Tarea> result = tareasUseCase.listarTareas(1L);

        assertNotNull(result);
        assertEquals(tareas, result);
        verify(tareasRepository, times(1)).listarTareas(1L);
    }

    @Test
    void obtenerTareaPorId_ShouldReturnTarea() {
        Tarea tarea = Tarea.builder()
                .id(1L)
                .title("Test Tarea")
                .description("This is a test tarea")
                .dueDate(new Date())
                .status("Pending")
                .build();
        when(tareasRepository.buscarPorId(1L)).thenReturn(tarea);

        Tarea result = tareasUseCase.obtenerTareaPorId(1L);

        assertNotNull(result);
        assertEquals(tarea, result);
        verify(tareasRepository, times(1)).buscarPorId(1L);
    }

    @Test
    void actualizarTarea_ShouldUpdateAndReturnTarea() {
        Tarea existingTarea = Tarea.builder()
                .id(1L)
                .title("Test Tarea")
                .description("This is a test tarea")
                .dueDate(new Date())
                .status("Pending")
                .build();
        Tarea updatedTarea = Tarea.builder()
                .id(1L)
                .title("Updated Tarea")
                .description("This is an updated test tarea")
                .dueDate(new Date())
                .status("Completed")
                .build();

        when(tareasRepository.buscarPorId(1L)).thenReturn(existingTarea);
        when(tareasRepository.guardar(existingTarea)).thenReturn(updatedTarea);

        Tarea result = tareasUseCase.actualizarTarea(1L, updatedTarea);

        assertNotNull(result);
        assertEquals(updatedTarea.getTitle(), result.getTitle());
        assertEquals(updatedTarea.getDescription(), result.getDescription());
        verify(tareasRepository, times(1)).buscarPorId(1L);
        verify(tareasRepository, times(1)).guardar(existingTarea);
    }

    @Test
    void actualizarTarea_ShouldThrowExceptionIfTareaNotFound() {
        when(tareasRepository.buscarPorId(1L)).thenReturn(null);

        Tarea updatedTarea = Tarea.builder()
                .id(1L)
                .title("Test Tarea")
                .description("This is a test tarea")
                .dueDate(new Date())
                .status("Pending")
                .build();

        assertThrows(BusinessException.class, () -> tareasUseCase.actualizarTarea(1L, updatedTarea));
        verify(tareasRepository, times(1)).buscarPorId(1L);
        verify(tareasRepository, never()).guardar(any());
    }

    @Test
    void eliminarTarea_ShouldReturnTrueIfTareaExists() {
        Tarea tarea = Tarea.builder()
                .id(1L)
                .title("Test Tarea")
                .description("This is a test tarea")
                .dueDate(new Date())
                .status("Pending")
                .build();
        when(tareasRepository.buscarPorId(1L)).thenReturn(tarea);

        boolean result = tareasUseCase.eliminarTarea(1L);

        assertTrue(result);
        verify(tareasRepository, times(1)).buscarPorId(1L);
        verify(tareasRepository, times(1)).eliminar(1L);
    }

    @Test
    void eliminarTarea_ShouldReturnFalseIfTareaDoesNotExist() {
        when(tareasRepository.buscarPorId(1L)).thenReturn(null);

        boolean result = tareasUseCase.eliminarTarea(1L);

        assertFalse(result);
        verify(tareasRepository, times(1)).buscarPorId(1L);
        verify(tareasRepository, never()).eliminar(any());
    }
}