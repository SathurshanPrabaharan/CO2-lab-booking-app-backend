package com.userservice.DTO.Response.Staff;

import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Staff;
import com.userservice.Models.SupportModels.Course;
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
    private UUID objectId;
    private String firstName;
    private String lastName;
    private String displayName;
    private String mobile;
    private GENDER gender;
    private String userRole;
    private String profession;
    private String department;
    private Set<String> responsibleCourses = new HashSet<>();
    private String userPrincipalName;
    private String contact_email;
    private String photoUrl;
    private Boolean isInitalLogged;
    private Boolean accountEnabled = false;
    private STATUS status;

    public ModifiedStaff(Staff staff) {
        this.id = staff.getId();
        this.objectId = staff.getObjectId();
        this.firstName = staff.getFirstName();
        this.lastName = staff.getLastName();
        this.displayName = staff.getDisplayName();
        this.mobile = staff.getMobile();
        this.gender = staff.getGender();
        this.userRole = staff.getUserRole().getKey();
        this.userPrincipalName = staff.getUserPrincipalName();
        this.contact_email = staff.getContact_email();
        this.photoUrl = staff.getPhotoUrl();
        this.isInitalLogged = staff.getIsInitalLogged();
        this.accountEnabled = staff.getAccountEnabled();
        this.status = staff.getStatus();

        Set<Course> responsibleCourses = staff.getResponsibleCourses();
        if (responsibleCourses != null) {
            for (Course course : responsibleCourses) {
                this.responsibleCourses.add(course.getName());
            }
        }

        if (staff.getProfession() != null) {
            this.profession = staff.getProfession().getName();
        }

        if (staff.getDepartment() != null) {
            this.department = staff.getDepartment().getName();
        }

    }

}
