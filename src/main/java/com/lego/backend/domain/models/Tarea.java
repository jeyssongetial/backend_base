package com.lego.backend.domain.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Tarea {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private String status;
    private Long assignedTo;
    private Long createdBy;
}
