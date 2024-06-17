package com.userservice.DTO.Response.Admin;

import com.userservice.DTO.Response.Admin.ModifiedAdmin;
import com.userservice.Models.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminListResponse {

    private String message;
    private ResultAdmin data;


    public AdminListResponse(String message, Page<Admin> filteredAdmins) {
        this.message = message;
        this.data = new ResultAdmin(filteredAdmins);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultAdmin {
    private long total;
    private List<ModifiedAdmin> results;

    ResultAdmin(Page<Admin> results) {
        this.total = results.getTotalElements();

        List<ModifiedAdmin> resultModifiedAdmins = new ArrayList<>();
        for(Admin currentAdmin: results.getContent()){
            ModifiedAdmin temp = new ModifiedAdmin(currentAdmin);
            resultModifiedAdmins.add(temp);
        }

        this.results = resultModifiedAdmins;

    }

}

