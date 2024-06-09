package com.inventoryservice.DTO.Request.Software;

import com.inventoryservice.Validations.ValidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoftwareUpdateRequest {
    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @NotEmpty(message = "Invalid version: version cannot be empty")
    private String version;

    private String description;

    private String category;

    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

}