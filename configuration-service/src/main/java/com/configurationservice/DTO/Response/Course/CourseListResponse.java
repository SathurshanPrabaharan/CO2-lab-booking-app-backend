package com.configurationservice.DTO.Response.Course;

import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseListResponse {

    private String message;
    private ResponseCoursePaginatedList data;

    public CourseListResponse(String message, List<Course> foundedCourses, int page, int size) {
        this.message = message;
        this.data = new ResponseCoursePaginatedList(foundedCourses, page, size);
    }

    public CourseListResponse(String message, List<Course> foundedCourses) {
        this.message = message;
        this.data = new ResponseCoursePaginatedList(foundedCourses);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCoursePaginatedList {
    private int total;
    private List<ResponseCourseList> results;

    public ResponseCoursePaginatedList(List<Course> results, Integer page, Integer size) {
        this.results = new ArrayList<>();

        if (results != null && !results.isEmpty()) {
            this.total = results.size();
            int startIndex = (page - 1) * size;
            int endIndex = Math.min(page * size, total);

            List<Course> paginatedAdmins;
            if (startIndex < total) {
                paginatedAdmins = results.subList(startIndex, endIndex);
            } else {
                paginatedAdmins = new ArrayList<>();
            }

            for (Course course : paginatedAdmins) {
                this.results.add(new ResponseCourseList(course));
            }
        } else {
            this.total = 0;
            this.results = new ArrayList<>();
        }
    }

    public ResponseCoursePaginatedList(List<Course> results) {
        this.total = results.size();
        this.results = new ArrayList<>();
        for (Course course : results) {
            this.results.add(new ResponseCourseList(course));
        }
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCourseList {

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

    public ResponseCourseList(Course course) {
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
