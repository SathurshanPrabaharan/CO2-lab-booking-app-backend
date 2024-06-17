package com.userservice.Controllers;


import com.userservice.DTO.Request.Admin.AdminArchiveRequest;
import com.userservice.DTO.Request.Admin.AdminCreateRequest;
import com.userservice.DTO.Request.Admin.AdminUpdateRequest;
import com.userservice.DTO.Response.ResponseMessage;
import com.userservice.DTO.Response.Admin.AdminDetailsResponse;
import com.userservice.DTO.Response.Admin.AdminListResponse;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Admin;
import com.userservice.Services.AdminService;
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
@RequestMapping("/api/v1/users/admins")
@Tag(name = "Admin Controller", description = "Endpoints for admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Create Admin", description = "Create admin with role privileges")
    @PostMapping
    public ResponseEntity<Object> saveAdmin(@RequestBody @Valid AdminCreateRequest admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        String message = "Admin created successfully";
        AdminDetailsResponse response = new AdminDetailsResponse(message, savedAdmin);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Admins",
            description = "Get All admins by applying page,size,userRoleId,professionId,departmentId,createdBy,gender,status filters")
    @GetMapping
    public ResponseEntity<Object> filterAdmin(
            @RequestParam(required = false) UUID userRoleId,
            @RequestParam(required = false) UUID professionId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Admin> filteredAdmins = adminService.filterAdmin(userRoleId, professionId, departmentId,gender, status, createdBy, page-1, size);
        String message = "Admins fetched successfully";
        AdminListResponse response = new AdminListResponse(message, filteredAdmins);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get Admin Details", description = "Get all details of the associated admin")
    @GetMapping("{id}")
    public ResponseEntity<Object> getAdminDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Admin admin = adminService.findById(id);
        String message = "Admin details fetched successfully";
        AdminDetailsResponse response = new AdminDetailsResponse(message, admin);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Update Admin", description = "Update the admin except userPrincipleName, objectId")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateAdmin(@PathVariable UUID id ,@RequestBody @Valid AdminUpdateRequest request) throws ResourceNotFoundException {
        Admin updatedAdmin  = adminService.updateAdmin(id,request);
        String message = "Admin updated successfully";
        AdminDetailsResponse response = new AdminDetailsResponse(message,updatedAdmin);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


   @Operation(summary = "Archive Admin", description = "Archive the Admin -> Status=ARCHIVED and Disable Azure Ad user account")
   @DeleteMapping("{id}")
   public ResponseEntity<Object> archiveAdmin(@PathVariable UUID id, @RequestBody AdminArchiveRequest request) throws ResourceNotFoundException {
       adminService.archiveAdmin(id,request);
       String message = "Admin archived successfully";
       ResponseMessage response = new ResponseMessage(message);
       return new ResponseEntity<>(response,HttpStatus.OK);
   }



}

