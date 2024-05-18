package com.configurationservice.DTO.Request.Department;

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
public class DepartmentCreateRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @NotNull(message = "Invalid hodId: hodId cannot be null")
    private UUID hodId;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;




}
