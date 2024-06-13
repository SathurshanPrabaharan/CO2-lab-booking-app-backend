package com.userservice.DTO.Request.Admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminArchiveRequest {

    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;


}
