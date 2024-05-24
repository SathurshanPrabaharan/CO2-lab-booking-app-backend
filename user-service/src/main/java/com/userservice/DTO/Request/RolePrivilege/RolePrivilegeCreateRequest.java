package com.userservice.DTO.Request.RolePrivilege;

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
public class RolePrivilegeCreateRequest {

    @NotEmpty(message = "Invalid key: Key cannot be empty")
    private String key;

    @NotEmpty(message = "Invalid title: Title cannot be empty")
    private String title;


    private UUID createdBy;


}
