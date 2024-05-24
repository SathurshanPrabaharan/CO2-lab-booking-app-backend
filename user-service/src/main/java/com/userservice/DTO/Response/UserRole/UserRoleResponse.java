package com.userservice.DTO.Response.UserRole;


import com.userservice.Models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleResponse {

    private String message;
    private UserRole data;

}