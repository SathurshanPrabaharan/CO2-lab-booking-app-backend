package com.userservice.DTO.Request.Student;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.RegEx;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {


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

    @NotNull(message = "Invalid semester: semester cannot be null")
    @Positive(message = "Invalid semester: semester must be positive")
    private Short semester;

    @NotBlank(message = "Invalid regNum: Registration number cannot be empty")
    @Pattern(regexp = "20[0-9]{2}/[A-Z]/[0-9]{3}", message = "Invalid regNum: Registration number format should be '20xx/E/xxx' : x-number")
    private String regNum;


    private UUID departmentId;


    private Set<UUID> currentCourseIds;

    // If not provided, value will be initiated in the service
    private String userPrincipalName;

    @NotEmpty(message = "Invalid tempPassword: Temporary Password cannot be empty")
    private String tempPassword;

    private String photoUrl;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;



}
