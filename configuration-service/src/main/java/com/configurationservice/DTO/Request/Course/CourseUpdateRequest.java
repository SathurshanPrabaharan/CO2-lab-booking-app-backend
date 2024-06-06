package com.configurationservice.DTO.Request.Course;

import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Validations.ValidCourseType;
import com.configurationservice.Validations.ValidStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseUpdateRequest {

    @NotEmpty(message = "Invalid Code: Code cannot be empty")
    private String code;

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @NotNull(message = "Invalid type: type cannot be null")
    @ValidCourseType(message = "Invalid type: type must be one of TECHNICAL or NON_TECHNICAL")
    private COURSE_TYPE type;

    @NotNull(message = "Invalid departmentId: departmentId cannot be null")
    private UUID departmentId;


    @NotNull(message = "Invalid departmentId: departmentId cannot be null")
    @Min(value = 1, message = "Semester must be greater than or equal to 1")
    @Max(value = 8, message = "Semester must be less than or equal to 8")
    private short semester;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;
    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

}
