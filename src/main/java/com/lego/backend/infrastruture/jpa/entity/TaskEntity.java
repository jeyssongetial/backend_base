package com.lego.backend.infrastruture.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private UserEntity assignedTo;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private UserEntity createdBy;
}
