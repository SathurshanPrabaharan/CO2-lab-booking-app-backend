package com.bookingservice.DTO.Response.SupportModelResponses;


import com.bookingservice.Enums.GENDER;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Models.SupportModels.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedAdmin {

    private UUID id;
    private UUID objectId;
    private String firstName;
    private String lastName;
    private String displayName;
    private String mobile;
    private GENDER gender;
    private String userRole;
    private String profession;
    private String department;
    private String userPrincipalName;
    private String contact_email;
    private String photoUrl;
    private Boolean isInitalLogged;
    private Boolean accountEnabled = false;
    private STATUS status;

    public ModifiedAdmin(Admin admin) {
        this.id = admin.getId();
        this.objectId = admin.getObjectId();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.displayName = admin.getDisplayName();
        this.mobile = admin.getMobile();
        this.gender = admin.getGender();
        this.userRole = admin.getUserRole().getKey();
        this.userPrincipalName = admin.getUserPrincipalName();
        this.contact_email = admin.getContact_email();
        this.photoUrl = admin.getPhotoUrl();
        this.isInitalLogged = admin.getIsInitalLogged();
        this.accountEnabled = admin.getAccountEnabled();
        this.status = admin.getStatus();

        if(admin.getProfession() != null){
            this.profession=admin.getProfession().getName();
        }

        if(admin.getDepartment() != null){
            this.department=admin.getDepartment().getName();
        }



    }

}