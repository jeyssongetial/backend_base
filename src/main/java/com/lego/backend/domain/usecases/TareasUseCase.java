package com.lego.backend.domain.usecases;

import com.lego.backend.domain.models.Tarea;

import java.util.List;

public class TareasUseCase {
    public Tarea crearTarea(Tarea tarea) {
        // Aquí se implementaría la lógica para crear una tarea
        // Por ejemplo, guardar la tarea en una base de datos
        // Retornamos la tarea creada
        return tarea;
    }

    public List<Tarea> listarTareas() {
        return null;
    }

    public Tarea obtenerTareaPorId(Long id) {
        // Aquí se implementaría la lógica para obtener una tarea por su ID
        // Por ejemplo, buscar la tarea en una base de datos
        // Retornamos la tarea encontrada
        return null;
    }

    public Tarea actualizarTarea(Long id, Tarea domain) {
        // Aquí se implementaría la lógica para actualizar una tarea
        // Por ejemplo, buscar la tarea por ID y actualizar sus campos
        // Retornamos la tarea actualizada
        return domain;
    }

    public boolean eliminarTarea(Long id) {
        return false;
    }
}
