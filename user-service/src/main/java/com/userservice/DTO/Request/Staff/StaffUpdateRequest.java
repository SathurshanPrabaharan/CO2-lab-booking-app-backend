package com.userservice.DTO.Request.Staff;

import com.userservice.Validations.ValidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffUpdateRequest {


    @NotEmpty(message="Invalid first name : First name cannot be empty" )
    private String firstName;

    @NotEmpty(message="Invalid last name : Last name cannot be empty" )
    private String lastName;

    @NotEmpty(message="Invalid display name : Display name cannot be empty" )
    private String displayName;

    private String mobile;


    private String gender;

    @NotNull(message = "Invalid userRoleId: userRoleId cannot be null")
    private UUID userRoleId;

    private UUID professionId;

    private UUID departmentId;

    private Set<UUID> responsibleCourseIds;

    private String contact_email;

    private String photoUrl;

    private Boolean isInitalLogged;

    private String verifyToken;

    private LocalDateTime tokenIssuedAt;


    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

    @Builder.Default
    private boolean wantToEnableAccount=false;

}
