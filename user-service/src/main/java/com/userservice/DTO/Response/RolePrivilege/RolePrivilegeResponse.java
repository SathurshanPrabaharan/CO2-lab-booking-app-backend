package com.userservice.DTO.Response.RolePrivilege;


import com.userservice.Models.RolePrivilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePrivilegeResponse {

    private String message;
    private RolePrivilege data;

}