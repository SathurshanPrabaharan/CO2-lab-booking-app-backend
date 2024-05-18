package com.configurationservice.DTO.Response.Course;

import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDetailsResponse {

    private String message;
    private ResponseCourseDetails data;


    public CourseDetailsResponse(String message, Course foundedAdmin) {
        this.message = message;
        this.data = new ResponseCourseDetails(foundedAdmin);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCourseDetails {

    private String code;
    private String name;
    private COURSE_TYPE type;
    private UUID departmentId;
    private short semester;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;

    public ResponseCourseDetails(Course course) {
        this.code = course.getCode();
        this.name = course.getName();
        this.type = course.getType();
        this.departmentId = course.getDepartmentId();
        this.semester = course.getSemester();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
        this.createdBy = course.getCreatedBy();
        this.updatedBy = course.getUpdatedBy();
        this.status = course.getStatus();
    }
}
