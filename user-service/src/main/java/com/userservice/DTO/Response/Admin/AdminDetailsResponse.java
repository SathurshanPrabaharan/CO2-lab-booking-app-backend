package com.userservice.DTO.Response.Admin;


import com.userservice.DTO.Response.Profession.ModifiedProfession;
import com.userservice.DTO.Response.SupportModelResponses.ModifiedDepartment;
import com.userservice.DTO.Response.UserRole.ModifiedUserRole;
import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Admin;
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
    private UUID objectId;
    private String firstName;
    private String lastName;
    private String displayName;
    private String mobile;
    private GENDER gender;
    private ModifiedUserRole userRole;
    private ModifiedProfession profession;
    private ModifiedDepartment department;
    private String userPrincipalName;
    private String contact_email;
    private String photoUrl;
    private Boolean isInitalLogged;
    private String verifyToken;
    private LocalDateTime tokenIssuedAt;
    private Boolean accountEnabled = false;
    private STATUS status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public ResponseAdminDetails(Admin admin) {
        this.id = admin.getId();
        this.objectId = admin.getObjectId();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.displayName = admin.getDisplayName();
        this.mobile = admin.getMobile();
        this.gender = admin.getGender();
        this.userRole = new ModifiedUserRole(admin.getUserRole());
        this.userPrincipalName = admin.getUserPrincipalName();
        this.contact_email = admin.getContact_email();
        this.photoUrl = admin.getPhotoUrl();
        this.isInitalLogged = admin.getIsInitalLogged();
        this.verifyToken = admin.getVerifyToken();
        this.tokenIssuedAt = admin.getTokenIssuedAt();
        this.accountEnabled = admin.getAccountEnabled();
        this.status = admin.getStatus();
        this.createdAt = admin.getCreatedAt();
        this.updatedAt = admin.getUpdatedAt();
        this.createdBy = admin.getCreatedBy();
        this.updatedBy = admin.getUpdatedBy();

        if (admin.getDepartment() != null) {
            this.department = new ModifiedDepartment(admin.getDepartment());
        }
        if (admin.getProfession() != null) {
            this.profession = new ModifiedProfession(admin.getProfession());
        }



    }
}
