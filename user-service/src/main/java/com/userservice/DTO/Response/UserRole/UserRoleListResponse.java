package com.userservice.DTO.Response.UserRole;


import com.userservice.DTO.Response.RolePrivilege.ModifiedRolePrivilege;
import com.userservice.Models.RolePrivilege;
import com.userservice.Models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleListResponse {

    private String message;
    private ResultUserRole data;


    public UserRoleListResponse(String message, List<UserRole> foundedUserRoles, int page, int size) {
        this.message = message;
        this.data = new ResultUserRole(foundedUserRoles,page,size);
    }

    public UserRoleListResponse(String message, List<UserRole> foundedUserRoles) {
        this.message = message;
        this.data = new ResultUserRole(foundedUserRoles);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultUserRole {
    private int total;
    private List<ModifiedUserRole> results;

    ResultUserRole(List<UserRole> results, Integer page, Integer size) {
        this.total = results.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(page * size, total);

        List<UserRole> paginatedResult;
        if (startIndex < total) {
            paginatedResult = results.subList(startIndex, endIndex);
        } else {
            paginatedResult = Collections.emptyList();
        }

        List<ModifiedUserRole> resultModifiedUserRoles = new ArrayList<>();
        for(UserRole currentUserRole: paginatedResult){
            ModifiedUserRole temp = new ModifiedUserRole(currentUserRole);
            resultModifiedUserRoles.add(temp);
        }

        this.results = resultModifiedUserRoles;
    }

    ResultUserRole(List<UserRole> results) {
        this.total = results.size();

        List<ModifiedUserRole> resultModifiedUserRoles = new ArrayList<>();
        for(UserRole currentUserRole: results){
            ModifiedUserRole temp = new ModifiedUserRole(currentUserRole);
            resultModifiedUserRoles.add(temp);
        }

        this.results = resultModifiedUserRoles;

    }

}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ModifiedUserRole {

    private UUID id;
    private String key;
    private String title;
    private Set<ModifiedRolePrivilege> privilegeSet;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    ModifiedUserRole(UserRole userRole ){
        this.id=userRole.getId();
        this.key= userRole.getKey();
        this.title= userRole.getKey();
        this.createdAt=userRole.getCreatedAt();
        this.updatedAt=userRole.getUpdatedAt();
        this.createdBy=userRole.getCreatedBy();
        this.updatedBy=userRole.getUpdatedBy();

        Set<ModifiedRolePrivilege> modifiedRolePrivilegeSet = new HashSet<>();
        for(RolePrivilege rolePrivilege: userRole.getPrivilegeSet()){
            //modify the rolePrivilege
            ModifiedRolePrivilege temp = new ModifiedRolePrivilege(rolePrivilege);
            //Add to set
            modifiedRolePrivilegeSet.add(temp);
        }
        //Add the result to final result
        this.privilegeSet=modifiedRolePrivilegeSet;

    }

}

