package com.inventoryservice.DTO.Request.Software;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class SoftwareCreateRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @NotEmpty(message = "Invalid version: version cannot be empty")
    private String version;

    private String description;

    private String category;

    @NotNull(message = "Invalid CreatedBy: CreatedBy cannot be null")
    private UUID createdBy;


}
