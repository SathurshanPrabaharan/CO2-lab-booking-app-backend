package com.userservice.DTO.Request.RolePrivilege;

import com.userservice.Validations.ValidStatus;
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
public class RolePrivilegeUpdateRequest {


    @NotEmpty(message = "Invalid Title : Title cannot be empty")
    private String title;

    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

}
