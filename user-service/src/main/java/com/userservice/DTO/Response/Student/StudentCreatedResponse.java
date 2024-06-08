package com.userservice.DTO.Response.Student;

import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Student;
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
public class StudentCreatedResponse {

    private String message;
    private ResponseCreatedStudent data;


    public StudentCreatedResponse(String message, Student foundedStudent) {
        this.message = message;
        this.data = new ResponseCreatedStudent(foundedStudent);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCreatedStudent {
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
    private String verifyToken;
    private LocalDateTime tokenIssuedAt;
    private Boolean accountEnabled = false;
    private STATUS status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public ResponseCreatedStudent(Student student) {
        this.id = student.getId();
        this.objectId = student.getObjectId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.displayName = student.getDisplayName();
        this.mobile = student.getMobile();
        this.gender = student.getGender();
        this.userRole = student.getUserRole().getKey();
        this.userPrincipalName = student.getUserPrincipalName();
        this.contact_email = student.getContact_email();
        this.photoUrl = student.getPhotoUrl();
        this.isInitalLogged = student.getIsInitalLogged();
        this.verifyToken = student.getVerifyToken();
        this.tokenIssuedAt = student.getTokenIssuedAt();
        this.accountEnabled = student.getAccountEnabled();
        this.status = student.getStatus();
        this.createdAt = student.getCreatedAt();
        this.updatedAt = student.getUpdatedAt();
        this.createdBy = student.getCreatedBy();
        this.updatedBy = student.getUpdatedBy();

        Set<Course> temp = student.getCurrentCourses();
        if (temp != null) {
            for (Course course : temp) {
                this.responsibleCourses.add(course.getName());
            }
        }

        if(student.getProfession() != null){
            this.profession = student.getProfession().getName();
        }

        if(student.getDepartment() != null){
            this.department = student.getDepartment().getName();
        }

    }
}
