package com.configurationservice.Models;

import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    private String code;

    @Column( nullable = false)
    private String name;

    @Column( nullable = false)
    private COURSE_TYPE type;

    @Column(name = "department_id",nullable = false)
    private UUID departmentId;

    @Column( nullable = false)
    @Min(value = 1, message = "Semester must be greater than or equal to 1")
    @Max(value = 8, message = "Semester must be less than or equal to 8")
    private short semester;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column( nullable = false)
    private STATUS status;

}
