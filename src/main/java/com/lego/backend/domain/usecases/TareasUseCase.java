package com.lego.backend.domain.usecases;

import com.lego.backend.domain.ex.BusinessException;
import com.lego.backend.domain.models.Tarea;
import com.lego.backend.domain.repository.TareasRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TareasUseCase {
    private final TareasRepository tareasRepository;

    public Tarea crearTarea(Tarea tarea) {

        return tareasRepository.guardar(tarea);
    }

    public List<Tarea> listarTareas(Long userId) {
        return tareasRepository.listarTareas(userId);
    }

    public Tarea obtenerTareaPorId(Long id) {
        // Lógica para obtener una tarea por su ID
        return tareasRepository.buscarPorId(id);
    };


public Tarea actualizarTarea(Long id, Tarea domain) {
    Tarea tareaExistente = tareasRepository.buscarPorId(id);
    if (tareaExistente == null) {
        throw BusinessException.Type.ERROR_TAREA_NO_ENCONTRADA.build();
    }
    // Actualizamos los campos necesarios
    tareaExistente.setTitle(domain.getTitle());
    tareaExistente.setDescription(domain.getDescription());
    tareaExistente.setDueDate(domain.getDueDate());
    // Guardamos la tarea actualizada
    return tareasRepository.guardar(tareaExistente);
}

    public boolean eliminarTarea(Long id) {
        Tarea tareaExistente = tareasRepository.buscarPorId(id);
        if (tareaExistente != null) {
            tareasRepository.eliminar(id); // Lógica para eliminar la tarea
            return true; // Retornamos true si la tarea fue eliminada exitosamente
        }
        return false; // Retornamos false si no se encontró la tarea
    }
}



