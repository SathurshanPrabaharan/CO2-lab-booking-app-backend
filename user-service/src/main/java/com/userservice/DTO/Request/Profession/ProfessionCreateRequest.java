package com.userservice.DTO.Request.Profession;

import com.userservice.Validations.ValidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProfessionCreateRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;





}
