package com.lego.backend.infrastruture.controller;


import com.lego.backend.domain.models.Tarea;
import com.lego.backend.domain.usecases.TareasUseCase;
import com.lego.backend.infrastruture.dto.TareaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tareas")
public class TareasController {

    private final TareasUseCase tareasUseCase;

    @PostMapping("/crear")
    @Operation(summary = "Crear Tarea",
            description = "Servicio para crear una nueva tarea",
            tags = {"Tareas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Tarea creada correctamente",
                         content = @io.swagger.v3.oas.annotations.media.Content(
                             mediaType = "application/json",
                             schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "400", description = "Error de negocio o validación",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<Tarea> crearTarea(@Valid @RequestBody TareaDto tareaDto) {
        return ResponseEntity.ok(tareasUseCase.crearTarea(tareaDto.toDomain()));
    }

    // ver lista de tareas
    @GetMapping("/listar")
    @Operation(summary = "Listar Tareas",
            description = "Servicio para listar todas las tareas",
            tags = {"Tareas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Lista de tareas obtenida correctamente",
                         content = @io.swagger.v3.oas.annotations.media.Content(
                             mediaType = "application/json",
                             schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "400", description = "Error de negocio o validación",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<List<Tarea>> listarTareas(@PathVariable Long id) {
        return ResponseEntity.ok(tareasUseCase.listarTareas(id));
    }


    //ver detalles d euna tarea especifica
    @GetMapping("/{id}")
    @Operation(summary = "Obtener Detalles de Tarea",
            description = "Servicio para obtener los detalles de una tarea específica por ID",
            tags = {"Tareas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Detalles de la tarea obtenidos correctamente",
                         content = @io.swagger.v3.oas.annotations.media.Content(
                             mediaType = "application/json",
                             schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        Tarea tarea = tareasUseCase.obtenerTareaPorId(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarea);
    }

    // Actualizar una tarea
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar Tarea",
            description = "Servicio para actualizar una tarea existente por ID",
            tags = {"Tareas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Tarea actualizada correctamente",
                         content = @io.swagger.v3.oas.annotations.media.Content(
                             mediaType = "application/json",
                             schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Tarea.class))),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de negocio o validación",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @Valid @RequestBody TareaDto tareaDto) {
        Tarea tareaActualizada = tareasUseCase.actualizarTarea(id, tareaDto.toDomain());
        if (tareaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tareaActualizada);
    }

    // Eliminar una tarea
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar Tarea",
            description = "Servicio para eliminar una tarea existente por ID",
            tags = {"Tareas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                         description = "Tarea eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        boolean eliminado = tareasUseCase.eliminarTarea(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
