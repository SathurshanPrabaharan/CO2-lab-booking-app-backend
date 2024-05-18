package com.configurationservice.Services;


import com.configurationservice.DTO.Request.Course.CourseCreateRequest;
import com.configurationservice.DTO.Request.Course.CourseUpdateRequest;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;

import java.util.List;
import java.util.UUID;


public interface CourseService {

    Course saveCourse(CourseCreateRequest courseCreateRequest);

    List<Course> getAllCourses();

    List<Course> getAllCourses(UUID createdBy, STATUS status);

    Course findById(UUID id);

    Course updateCourse(UUID id, CourseUpdateRequest courseUpdateRequest);


    void archiveCourse(UUID id);

}
