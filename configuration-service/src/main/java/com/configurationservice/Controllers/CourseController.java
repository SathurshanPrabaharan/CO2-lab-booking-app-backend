package com.configurationservice.Controllers;

import com.configurationservice.DTO.Request.Course.CourseCreateRequest;
import com.configurationservice.DTO.Request.Course.CourseUpdateRequest;
import com.configurationservice.DTO.Response.Course.CourseListResponse;
import com.configurationservice.DTO.Response.Course.CourseResponse;
import com.configurationservice.DTO.Response.Course.CourseResponseMessage;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Course;
import com.configurationservice.Repositories.CourseRepository;
import com.configurationservice.Services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    public CourseController(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseCreateRequest courseCreateRequest){
        Course savedCourse = courseService.saveCourse(courseCreateRequest);
        String message = "Course created successfully";
        CourseResponse response = new CourseResponse(message, savedCourse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<Object> getAllCourses(
//            @RequestParam(required = false) Integer page,
//            @RequestParam(required = false) Integer size,
//            @RequestParam(required = false) UUID createdBy,
//            @RequestParam(required = false) String status
//    ){
//        List<Course> foundedCourses;
//
//        if (createdBy == null && status == null){
//            foundedCourses = courseService.getAllCourses();
//        }else if(status == null){
//
//            foundedCourses = courseService.getAllCourses( createdBy, null);
//        }else{
//            foundedCourses = courseService.getAllCourses( createdBy, STATUS.valueOf(status));
//        }
//
//
//        String message = "Courses fetched successfully";
//
//        CourseListResponse response;
//        if(page==null && size==null) {
//            response = new CourseListResponse(message, foundedCourses);
//        }else{
//            response = new CourseListResponse(message, foundedCourses, page, size);
//        }
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//    @GetMapping("{id}")
//    public ResponseEntity<Object> getCourseDetails(@PathVariable UUID id) throws ResourceNotFoundException {
//        Course course = courseService.findById(id);
//        String message = "Course details fetched successfully";
//        CourseResponse response = new CourseResponse(message, course);
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//
//    @PutMapping("{id}")
//    public ResponseEntity<Object> updateCourse(@PathVariable UUID id ,@RequestBody @Valid CourseUpdateRequest courseUpdateRequest) throws ResourceNotFoundException {
//        Course updatedCourse  = courseService.updateCourse(id,courseUpdateRequest);
//        String message = "Course updated successfully";
//        CourseResponse response = new CourseResponse(message,updatedCourse);
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Object> archiveCourse(@PathVariable UUID id) throws ResourceNotFoundException {
//        courseService.archiveCourse(id);
//        String message = "Course archived successfully";
//        CourseResponseMessage response = new CourseResponseMessage(message);
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }





}
