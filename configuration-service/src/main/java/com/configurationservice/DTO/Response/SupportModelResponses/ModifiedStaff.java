package com.configurationservice.DTO.Response.SupportModelResponses;


import com.configurationservice.Enums.GENDER;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.SupportModels.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedStaff {

    private UUID id;
    private String firstName;
    private String lastName;
    private String mobile;
    private GENDER gender;
    private String profession;
    private String department;
    private String userPrincipalName;
    private String contact_email;
    private String photoUrl;
    private STATUS status;

    public ModifiedStaff(Staff staff) {
        this.id = staff.getId();
        this.firstName = staff.getFirstName();
        this.lastName = staff.getLastName();
        this.mobile = staff.getMobile();
        this.gender = staff.getGender();
        this.userPrincipalName = staff.getUserPrincipalName();
        this.contact_email = staff.getContact_email();
        this.photoUrl = staff.getPhotoUrl();
        this.status = staff.getStatus();

        if (staff.getProfession() != null) {
            this.profession = staff.getProfession().getName();
        }

        if (staff.getDepartment() != null) {
            this.department = staff.getDepartment().getName();
        }

    }

}
