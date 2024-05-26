package com.userservice.DTO.Response.Staff;

import com.userservice.DTO.Response.Course.ModifiedCourse;
import com.userservice.DTO.Response.Department.ModifiedDepartment;
import com.userservice.DTO.Response.Profession.ModifiedProfession;
import com.userservice.DTO.Response.UserRole.ModifiedUserRole;
import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Staff;
import com.userservice.Models.SupportModels.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDetailsResponse {

    private String message;
    private ResponseStaffDetails data;


    public StaffDetailsResponse(String message, Staff foundedStaff) {
        this.message = message;
        this.data = new ResponseStaffDetails(foundedStaff);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseStaffDetails {
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
    private Set<ModifiedCourse> responsibleCourses = new HashSet<>();
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

    public ResponseStaffDetails(Staff staff) {
        this.id = staff.getId();
        this.objectId = staff.getObjectId();
        this.firstName = staff.getFirstName();
        this.lastName = staff.getLastName();
        this.displayName = staff.getDisplayName();
        this.mobile = staff.getMobile();
        this.gender = staff.getGender();
        this.userRole = new ModifiedUserRole(staff.getUserRole());
        this.userPrincipalName = staff.getUserPrincipalName();
        this.contact_email = staff.getContact_email();
        this.photoUrl = staff.getPhotoUrl();
        this.isInitalLogged = staff.getIsInitalLogged();
        this.verifyToken = staff.getVerifyToken();
        this.tokenIssuedAt = staff.getTokenIssuedAt();
        this.accountEnabled = staff.getAccountEnabled();
        this.status = staff.getStatus();
        this.createdAt = staff.getCreatedAt();
        this.updatedAt = staff.getUpdatedAt();
        this.createdBy = staff.getCreatedBy();
        this.updatedBy = staff.getUpdatedBy();

        Set<Course> temp = staff.getResponsibleCourses();
        if (temp != null) {
            temp.stream()
                    .map(ModifiedCourse::new)
                    .forEach(this.responsibleCourses::add);
        }

        if(staff.getProfession() !=null){
            this.profession = new ModifiedProfession(staff.getProfession());
        }

        if(staff.getDepartment() !=null){
            this.department = new ModifiedDepartment(staff.getDepartment());
        }

    }
}
