package com.configurationservice.Controllers;

import com.configurationservice.DTO.Request.Course.CourseArchiveRequest;
import com.configurationservice.DTO.Request.Course.CourseCreateRequest;
import com.configurationservice.DTO.Request.Course.CourseUpdateRequest;
import com.configurationservice.DTO.Response.Course.CourseCreatedResponse;
import com.configurationservice.DTO.Response.Course.CourseDetailsResponse;
import com.configurationservice.DTO.Response.Course.CourseListResponse;
import com.configurationservice.DTO.Response.Course.CourseResponse;
import com.configurationservice.DTO.Response.ResponseMessage;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Course;
import com.configurationservice.Repositories.SupportRepositories.StaffRepository;
import com.configurationservice.Services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/configurations/courses")
@Tag(name = "Course Controller", description = "Endpoints for courses")
@CrossOrigin("http://localhost:5173")
//@CrossOrigin("http://192.168.52.120:5173/")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StaffRepository staffRepository;

    @Operation(summary = "Create course", description = "Create the course in the faculty")
    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseCreateRequest courseCreateRequest){
        Course savedCourse = courseService.saveCourse(courseCreateRequest);
        String message = "Course created successfully";
        CourseCreatedResponse response = new CourseCreatedResponse(message, savedCourse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Courses", description = "Get All courses by applying page,size,createdBy,status filters")
    @GetMapping
    public ResponseEntity<Object> filterCourse(
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) String courseType,
            @RequestParam(required = false) Short semester,
            @RequestParam(required = false) UUID responsibleStaffId,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ) {

        Page<Course> filteredCourses = courseService.filterCourse(departmentId,courseType,semester,responsibleStaffId,createdBy, status, page-1, size);
        String message = "Courses fetched successfully";
        CourseListResponse response = new CourseListResponse(message, filteredCourses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get Course Details", description = "Get all details of the course")
    @GetMapping("{id}")
    public ResponseEntity<Object> getCourseDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Course course = courseService.findById(id);
        String message = "Course details fetched successfully";
        CourseDetailsResponse response = new CourseDetailsResponse(message, course,staffRepository);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



    @Operation(summary = "Update Course", description = "Update the course")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable UUID id ,@RequestBody @Valid CourseUpdateRequest courseUpdateRequest) throws ResourceNotFoundException {
        Course updatedCourse  = courseService.updateCourse(id,courseUpdateRequest);
        String message = "Course updated successfully";
        CourseResponse response = new CourseResponse(message,updatedCourse);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Archive Course", description = "Archive the course -> Status=ARCHIVED")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveCourse(@PathVariable UUID id,@RequestBody CourseArchiveRequest request) throws ResourceNotFoundException {
        courseService.archiveCourse(id,request);
        String message = "Course archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}
