package com.userservice.Controllers;


import com.userservice.DTO.Request.Student.StudentArchiveRequest;
import com.userservice.DTO.Request.Student.StudentCreateRequest;
import com.userservice.DTO.Request.Student.StudentUpdateRequest;
import com.userservice.DTO.Response.ResponseMessage;
import com.userservice.DTO.Response.Student.StudentDetailsResponse;
import com.userservice.DTO.Response.Student.StudentListResponse;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Student;
import com.userservice.Services.StudentService;
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
@RequestMapping("/api/v1/users/students")
@Tag(name = "Student Controller", description = "Endpoints for students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Create Student", description = "Create student with role privileges")
    @PostMapping
    public ResponseEntity<Object> saveStudent(@RequestBody @Valid StudentCreateRequest student) {
        Student savedStudent = studentService.saveStudent(student);
        String message = "Student created successfully";
        StudentDetailsResponse response = new StudentDetailsResponse(message, savedStudent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Students",
            description = "Get All students by applying page,size,userRoleId,professionId,departmentId,courseId,createdBy,gender,semester,status filters")
    @GetMapping
    public ResponseEntity<Object> filterStudent(
            @RequestParam(required = false) UUID userRoleId,
            @RequestParam(required = false) UUID professionId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID courseId,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Short semester,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Student> filteredStudents = studentService.filterStudent(userRoleId, professionId, departmentId, courseId,gender,semester, status, createdBy, page-1, size);
        String message = "Students fetched successfully";
        StudentListResponse response = new StudentListResponse(message, filteredStudents);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get Student Details", description = "Get all details of the associated student")
    @GetMapping("{id}")
    public ResponseEntity<Object> getStudentDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Student student = studentService.findById(id);
        String message = "Student details fetched successfully";
        StudentDetailsResponse response = new StudentDetailsResponse(message, student);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Update Student", description = "Update the student except userPrincipleName, objectId")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable UUID id ,@RequestBody @Valid StudentUpdateRequest request) throws ResourceNotFoundException {
        Student updatedStudent  = studentService.updateStudent(id,request);
        String message = "Student updated successfully";
        StudentDetailsResponse response = new StudentDetailsResponse(message,updatedStudent);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Archive Student", description = "Archive the Student -> Status=ARCHIVED and Disable Azure Ad user account")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveStudent(@PathVariable UUID id, @RequestBody StudentArchiveRequest request) throws ResourceNotFoundException {
        studentService.archiveStudent(id,request);
        String message = "Student archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}

