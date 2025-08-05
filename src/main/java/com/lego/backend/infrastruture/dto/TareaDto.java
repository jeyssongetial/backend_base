package com.lego.backend.infrastruture.dto;

import com.lego.backend.domain.models.Tarea;
import com.lego.backend.domain.models.UserInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TareaDto {

    @NotBlank(message = "el nombre es obligatoria")
    @NotBlank(message = "El id es obligatorio")
    private String id;

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private Date dueDate;

    @NotBlank(message = "El estado es obligatorio")
    private String status;

    @NotNull(message = "El usuario asignado es obligatorio")
    private Long assignedTo;

    @NotNull(message = "El creador es obligatorio")
    private Long createdBy;

    public Tarea toDomain() {
        return Tarea.builder()
                .id(id)
                .title(title)
                .description(description)
                .dueDate(dueDate)
                .status(status)
                .assignedTo(assignedTo)
                .createdBy(createdBy)
                .build();
    }
}
