package com.configurationservice.Controllers;

import com.configurationservice.DTO.Request.Department.DepartmentArchiveRequest;
import com.configurationservice.DTO.Request.Department.DepartmentCreateRequest;
import com.configurationservice.DTO.Request.Department.DepartmentUpdateRequest;
import com.configurationservice.DTO.Response.Department.DepartmentCreatedResponse;
import com.configurationservice.DTO.Response.Department.DepartmentDetailsResponse;
import com.configurationservice.DTO.Response.Department.DepartmentListResponse;
import com.configurationservice.DTO.Response.Department.DepartmentResponse;
import com.configurationservice.DTO.Response.ResponseMessage;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Department;
import com.configurationservice.Repositories.SupportRepositories.StaffRepository;
import com.configurationservice.Services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/configuration/departments")
@Tag(name = "Department Controller", description = "Endpoints for departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StaffRepository staffRepository;

    @Operation(summary = "Create department", description = "Create the department in the faculty")
    @PostMapping
    public ResponseEntity<Object> saveDepartment(@RequestBody @Valid DepartmentCreateRequest departmentCreateRequest){
        Department savedDepartment = departmentService.saveDepartment(departmentCreateRequest);
        String message = "Department created successfully";
        DepartmentCreatedResponse response = new DepartmentCreatedResponse(message, savedDepartment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Departments", description = "Get All departments by applying page,size,createdBy,status filters")
    @GetMapping
    public ResponseEntity<Object> filterDepartment(
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ) {

        Page<Department> filteredDepartments = departmentService.filterDepartment(createdBy, status, page-1, size);
        String message = "Departments fetched successfully";
        DepartmentListResponse response = new DepartmentListResponse(message, filteredDepartments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get Department Details", description = "Get all details of the department")
    @GetMapping("{id}")
    public ResponseEntity<Object> getDepartmentDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Department department = departmentService.findById(id);
        String message = "Department details fetched successfully";
        DepartmentDetailsResponse response = new DepartmentDetailsResponse(message, department,staffRepository);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Update Department", description = "Update the department")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable UUID id ,@RequestBody @Valid DepartmentUpdateRequest departmentUpdateRequest) throws ResourceNotFoundException {
        Department updatedDepartment  = departmentService.updateDepartment(id,departmentUpdateRequest);
        String message = "Department updated successfully";
        DepartmentResponse response = new DepartmentResponse(message,updatedDepartment);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Archive Department", description = "Archive the department -> Status=ARCHIVED")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveDepartment(@PathVariable UUID id,@RequestBody DepartmentArchiveRequest request) throws ResourceNotFoundException {
        departmentService.archiveDepartment(id,request);
        String message = "Department archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}
