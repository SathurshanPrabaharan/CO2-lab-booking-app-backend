package com.userservice.DTO.Request.Staff;

import com.userservice.Enums.GENDER;
import com.userservice.Validations.ValidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class StaffCreateRequest {

    @NotEmpty(message="Invalid first name : First name cannot be empty" )
    private String firstName;

    @NotEmpty(message="Invalid last name : Last name cannot be empty" )
    private String lastName;

    @NotEmpty(message="Invalid display name : Display name cannot be empty" )
    private String displayName;

    private String mobile;

//    @NotNull(message = "Invalid gender: Gender cannot be null")
//    @ValidStatus(message = "Invalid gender: Gender must be one of MALE, FEMALE, or OTHER")
    private String gender;

    @NotNull(message = "Invalid userRoleId: userRoleId cannot be null")
    private UUID userRoleId;

    private UUID professionId;

    private Set<UUID> responsibleCourseIds;

    @NotEmpty(message = "Invalid userPrincipalName: User Principal Name cannot be empty")
    private String userPrincipalName;

    @NotEmpty(message = "Invalid tempPassword: Temporary Password cannot be empty")
    private String tempPassword;

    private String photoUrl;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;

}
