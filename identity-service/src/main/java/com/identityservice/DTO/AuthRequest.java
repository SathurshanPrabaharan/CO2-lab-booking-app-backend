package com.identityservice.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
public class AuthRequest {

    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String userName;

    @NotEmpty(message = "Invalid Password: Password cannot be empty")
    @Size(min = 8,message = "Password should be minimum 8 characters")
    private String password;
}
