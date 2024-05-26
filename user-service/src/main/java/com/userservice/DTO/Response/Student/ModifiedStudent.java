package com.userservice.DTO.Response.Student;

import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Student;
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
public class ModifiedStudent {

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
    private Set<String> currentCourses = new HashSet<>();
    private String userPrincipalName;
    private String contact_email;
    private String photoUrl;
    private Boolean isInitalLogged;
    private Boolean accountEnabled = false;
    private STATUS status;

    public ModifiedStudent(Student student) {
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
        this.accountEnabled = student.getAccountEnabled();
        this.status = student.getStatus();

        Set<Course> currentCourses = student.getCurrentCourses();
        if (currentCourses != null) {
            for (Course course : currentCourses) {
                this.currentCourses.add(course.getName());
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
