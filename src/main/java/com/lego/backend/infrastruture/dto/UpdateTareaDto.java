package com.lego.backend.infrastruture.dto;

import com.lego.backend.domain.models.Tarea;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UpdateTareaDto {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "El estado es obligatorio")
    private String status;


    public Tarea toDomain() {
        return Tarea.builder()
                .title(title)
                .description(description)
                .status(status)
                .build();
    }
}
