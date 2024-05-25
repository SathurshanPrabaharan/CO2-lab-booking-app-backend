package com.userservice.DTO.Response.UserRole;



import com.userservice.Models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

