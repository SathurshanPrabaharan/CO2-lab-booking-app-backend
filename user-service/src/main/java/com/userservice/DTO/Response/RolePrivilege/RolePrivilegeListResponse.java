package com.userservice.DTO.Response.RolePrivilege;


import com.userservice.Models.RolePrivilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePrivilegeListResponse {

    private String message;
    private ResultRolePrivilege data;


    public RolePrivilegeListResponse(String message, List<RolePrivilege> foundedRolePrivileges, int page, int size) {
        this.message = message;
        this.data = new ResultRolePrivilege(foundedRolePrivileges,page,size);
    }

    public RolePrivilegeListResponse(String message, List<RolePrivilege> foundedRolePrivileges) {
        this.message = message;
        this.data = new ResultRolePrivilege(foundedRolePrivileges);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultRolePrivilege {
    private int total;
    private List<RolePrivilege> results;

    ResultRolePrivilege(List<RolePrivilege> results, Integer page, Integer size) {
        this.total = results.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(page * size, total);
        if (startIndex < total) {
            this.results = results.subList(startIndex, endIndex);
        } else {
            this.results = Collections.emptyList();
        }
    }

    ResultRolePrivilege(List<RolePrivilege> results) {
        this.total = results.size();
        this.results = results;
    }

}


