package com.inventoryservice.DTO.Request.Software;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class SoftwareCreateRequest {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String version;
}
