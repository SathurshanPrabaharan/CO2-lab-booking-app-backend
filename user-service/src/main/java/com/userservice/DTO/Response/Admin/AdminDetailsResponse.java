package com.userservice.DTO.Response.Admin;

import com.userservice.Enums.STATUS;
import com.userservice.Models.Admin;
import com.userservice.Models.SupportModels.Profession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDetailsResponse {

    private String message;
    private ResponseAdminDetails data;


    public AdminDetailsResponse(String message, Admin foundedAdmin) {
        this.message = message;
        this.data = new ResponseAdminDetails(foundedAdmin);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseAdminDetails {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;
    private Profession profession;

    public ResponseAdminDetails(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.createdAt = admin.getCreatedAt();
        this.updatedAt = admin.getUpdatedAt();
        this.createdBy = admin.getCreatedBy();
        this.updatedBy = admin.getUpdatedBy();
        this.status = admin.getStatus();
        this.profession = admin.getProfession();
    }
}
