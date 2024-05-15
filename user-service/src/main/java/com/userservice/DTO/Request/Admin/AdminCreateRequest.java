package com.userservice.DTO.Request.Admin;

import com.userservice.Enums.STATUS;
import com.userservice.Models.Profession;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreateRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;

    @Email(message = "Invalid Email: Invalid email format")
    @NotEmpty(message = "Invalid Email: Email cannot be empty")
    private String email;

    @NotEmpty(message = "Invalid Password: Password cannot be empty")
    @Size(min = 8,message = "Password should be minimum 8 characters")
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Invalid createdBy: CreatedBy cannot be null")
    private UUID createdBy;

    @NotNull(message = "Invalid professionId: professionId cannot be null")
    private UUID professionId;


}
