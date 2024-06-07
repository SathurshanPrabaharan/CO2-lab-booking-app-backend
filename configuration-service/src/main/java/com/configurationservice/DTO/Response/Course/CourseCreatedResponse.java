package com.configurationservice.DTO.Response.Course;


import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaff;
import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaffSimple;
import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.SupportModels.Staff;
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
public class CourseCreatedResponse {

    private String message;
    private ResponseCreatedCourse data;


    public CourseCreatedResponse(String message, Course foundedCourse) {
        this.message = message;
        this.data = new ResponseCreatedCourse(foundedCourse);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCreatedCourse {
    private UUID id;
    private String code;
    private String name;
    private COURSE_TYPE courseType;
    private String department;
    private short semester;
    private ModifiedStaffSimple coordinator;
    private Set<ModifiedStaffSimple> responsibleStaffs = new HashSet<>();
    private STATUS status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public ResponseCreatedCourse(Course course) {
        this.id = course.getId();
        this.code= course.getCode();
        this.name=course.getName();
        this.courseType=course.getCourseType();
        this.semester=course.getSemester();
        this.status = course.getStatus();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
        this.createdBy = course.getCreatedBy();
        this.updatedBy = course.getUpdatedBy();

        if(course.getCoordinator() != null){
            this.coordinator = new ModifiedStaffSimple(course.getCoordinator());
        }

        if(course.getDepartment() != null){
            this.department = course.getDepartment().getName();
        }

        Set<Staff> temp = course.getResponsibleStaffs();
        if (temp != null) {
            for (Staff staff : temp) {
                this.responsibleStaffs.add(new ModifiedStaffSimple(staff));
            }
        }


    }
}
