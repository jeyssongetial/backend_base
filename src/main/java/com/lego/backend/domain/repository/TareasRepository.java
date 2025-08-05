package com.lego.backend.domain.repository;

import com.lego.backend.domain.models.Tarea;

import java.util.BitSet;
import java.util.List;

public interface TareasRepository
{
    Tarea guardar(Tarea tarea);
    Tarea buscarPorId(Long id);
    List<Tarea> listarTareas(Long userId);
    void eliminar(Long id);
}
