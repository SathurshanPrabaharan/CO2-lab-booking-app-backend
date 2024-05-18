package com.configurationservice.Controllers;

import com.configurationservice.DTO.Request.Department.DepartmentCreateRequest;
import com.configurationservice.DTO.Request.Department.DepartmentUpdateRequest;
import com.configurationservice.DTO.Response.Department.DepartmentListResponse;
import com.configurationservice.DTO.Response.Department.DepartmentResponse;
import com.configurationservice.DTO.Response.Department.DepartmentResponseMessage;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Department;
import com.configurationservice.Repositories.DepartmentRepository;
import com.configurationservice.Services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/configuration/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService) {
        super();
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Object> saveDepartment(@RequestBody @Valid DepartmentCreateRequest departmentCreateRequest){
        Department savedDepartment = departmentService.saveDepartment(departmentCreateRequest);
        String message = "Department created successfully";
        DepartmentResponse response = new DepartmentResponse(message, savedDepartment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllDepartments(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status
    ){
        List<Department> foundedDepartments;

        if (createdBy == null && status == null){
            foundedDepartments = departmentService.getAllDepartments();
        }else if(status == null){

            foundedDepartments = departmentService.getAllDepartments( createdBy, null);
        }else{
            foundedDepartments = departmentService.getAllDepartments( createdBy, STATUS.valueOf(status));
        }


        String message = "Departments fetched successfully";

        DepartmentListResponse response;
        if(page==null && size==null) {
            response = new DepartmentListResponse(message, foundedDepartments);
        }else{
            response = new DepartmentListResponse(message, foundedDepartments, page, size);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> getDepartmentDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Department department = departmentService.findById(id);
        String message = "Department details fetched successfully";
        DepartmentResponse response = new DepartmentResponse(message, department);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable UUID id ,@RequestBody @Valid DepartmentUpdateRequest departmentUpdateRequest) throws ResourceNotFoundException {
        Department updatedDepartment  = departmentService.updateDepartment(id,departmentUpdateRequest);
        String message = "Department updated successfully";
        DepartmentResponse response = new DepartmentResponse(message,updatedDepartment);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveDepartment(@PathVariable UUID id) throws ResourceNotFoundException {
        departmentService.archiveDepartment(id);
        String message = "Department archived successfully";
        DepartmentResponseMessage response = new DepartmentResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}
