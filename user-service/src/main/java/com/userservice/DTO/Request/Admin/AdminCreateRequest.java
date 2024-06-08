package com.userservice.DTO.Request.Admin;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreateRequest {


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

    // If not provided, value will be initiated in the service
    private String userPrincipalName;

    @NotEmpty(message = "Invalid tempPassword: Temporary Password cannot be empty")
    private String tempPassword;

    private String contact_email;

    private String photoUrl;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;



}
