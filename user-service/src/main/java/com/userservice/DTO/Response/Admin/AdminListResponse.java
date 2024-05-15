package com.userservice.DTO.Response.Admin;

import com.userservice.Enums.STATUS;
import com.userservice.Models.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminListResponse {

    private String message;
    private ResponseAdminPaginatedList data;

    public AdminListResponse(String message, List<Admin> foundedAdmins, int page, int size) {
        this.message = message;
        this.data = new ResponseAdminPaginatedList(foundedAdmins, page, size);
    }

    public AdminListResponse(String message, List<Admin> foundedAdmins) {
        this.message = message;
        this.data = new ResponseAdminPaginatedList(foundedAdmins);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseAdminPaginatedList {
    private int total;
    private List<ResponseAdminList> results;

    public ResponseAdminPaginatedList(List<Admin> results, Integer page, Integer size) {
        this.results = new ArrayList<>();

        if (results != null && !results.isEmpty()) {
            this.total = results.size();
            int startIndex = (page - 1) * size;
            int endIndex = Math.min(page * size, total);

            List<Admin> paginatedAdmins;
            if (startIndex < total) {
                paginatedAdmins = results.subList(startIndex, endIndex);
            } else {
                paginatedAdmins = new ArrayList<>();
            }

            for (Admin admin : paginatedAdmins) {
                this.results.add(new ResponseAdminList(admin));
            }
        } else {
            this.total = 0;
            this.results = new ArrayList<>();
        }
    }

    public ResponseAdminPaginatedList(List<Admin> results) {
        this.total = results.size();
        this.results = new ArrayList<>();
        for (Admin admin : results) {
            this.results.add(new ResponseAdminList(admin));
        }
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseAdminList {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;
    private String profession;

    public ResponseAdminList(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.createdAt = admin.getCreatedAt();
        this.updatedAt = admin.getUpdatedAt();
        this.createdBy = admin.getCreatedBy();
        this.updatedBy = admin.getUpdatedBy();
        this.status = admin.getStatus();
        this.profession = admin.getProfession() != null ? admin.getProfession().getName() : null;
    }
}
