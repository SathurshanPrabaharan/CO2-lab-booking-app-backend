package com.configurationservice.DTO.Request.Course;

import com.configurationservice.Models.SupportModels.Staff;
import com.configurationservice.Validations.ValidCourseType;
import com.configurationservice.Validations.ValidStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateRequest {

    @NotEmpty(message = "Invalid code: code cannot be empty")
    private String code;

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @NotNull(message = "Invalid courseType: courseType cannot be null")
    @ValidCourseType(message = "Invalid courseType: courseType must be one of TECHNICAL or NON_TECHNICAL")
    private String courseType;


    private UUID departmentId;

    private short semester;

    private UUID coordinatorId;

    private Set<UUID> responsibleStaffIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;




}
