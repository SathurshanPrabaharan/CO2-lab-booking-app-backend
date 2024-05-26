package com.userservice.DTO.Response.Student;

import com.userservice.DTO.Response.Course.ModifiedCourse;
import com.userservice.DTO.Response.Department.ModifiedDepartment;
import com.userservice.DTO.Response.Profession.ModifiedProfession;
import com.userservice.DTO.Response.UserRole.ModifiedUserRole;
import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Student;
import com.userservice.Models.SupportModels.Course;
import com.userservice.Models.SupportModels.Department;
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
public class StudentDetailsResponse {

    private String message;
    private ResponseStudentDetails data;


    public StudentDetailsResponse(String message, Student foundedStudent) {
        this.message = message;
        this.data = new ResponseStudentDetails(foundedStudent);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseStudentDetails {
    private UUID id;
    private UUID objectId;
    private String firstName;
    private String lastName;
    private String displayName;
    private String mobile;
    private GENDER gender;
    private ModifiedUserRole userRole;
    private ModifiedProfession profession;
    private Short semester;
    private String regNum;
    private ModifiedDepartment department;
    private Set<ModifiedCourse> currentCourses = new HashSet<>();
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

    public ResponseStudentDetails(Student student) {
        this.id = student.getId();
        this.objectId = student.getObjectId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.displayName = student.getDisplayName();
        this.mobile = student.getMobile();
        this.gender = student.getGender();
        this.userRole = new ModifiedUserRole(student.getUserRole());
        this.semester = student.getSemester();
        this.regNum = student.getRegNum();
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

        if (student.getDepartment() != null) {
            this.department = new ModifiedDepartment(student.getDepartment());
        }
        if (student.getProfession() != null) {
            this.profession = new ModifiedProfession(student.getProfession());
        }

        Set<Course> temp = student.getCurrentCourses();
        if (temp != null) {
            temp.stream()
                    .map(ModifiedCourse::new)
                    .forEach(this.currentCourses::add);
        }

    }
}
