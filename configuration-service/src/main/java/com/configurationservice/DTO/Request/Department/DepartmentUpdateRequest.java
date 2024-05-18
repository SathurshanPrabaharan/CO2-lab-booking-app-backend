package com.configurationservice.DTO.Request.Department;

import com.configurationservice.Validations.ValidStatus;
import jakarta.validation.constraints.*;
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
public class DepartmentUpdateRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @NotNull(message = "Invalid hodId: hodId cannot be null")
    private UUID hodId;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

}
