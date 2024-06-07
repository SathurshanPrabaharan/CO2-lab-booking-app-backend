package com.configurationservice.Services;


import com.configurationservice.DTO.Request.Course.CourseArchiveRequest;
import com.configurationservice.DTO.Request.Course.CourseCreateRequest;
import com.configurationservice.DTO.Request.Course.CourseUpdateRequest;
import com.configurationservice.Models.Course;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface CourseService {

    Course saveCourse(CourseCreateRequest admin);

    List<Course> getAllCourses();

    Page<Course> filterCourse(UUID departmentId, String courseType, Short semester, UUID responsibleStaffId, UUID createdBy, String status, int page, int size);

    Course findById(UUID id);

    Course updateCourse(UUID id, CourseUpdateRequest courseUpdateRequest);


    void archiveCourse(UUID id, CourseArchiveRequest request);

}
