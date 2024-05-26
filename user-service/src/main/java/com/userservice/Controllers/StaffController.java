package com.userservice.Controllers;


import com.userservice.DTO.Request.Staff.StaffArchiveRequest;
import com.userservice.DTO.Request.Staff.StaffCreateRequest;
import com.userservice.DTO.Request.Staff.StaffUpdateRequest;
import com.userservice.DTO.Response.ResponseMessage;
import com.userservice.DTO.Response.Staff.StaffDetailsResponse;
import com.userservice.DTO.Response.Staff.StaffListResponse;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Staff;
import com.userservice.Services.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/staffs")
@Tag(name = "Staff Controller", description = "Endpoints for staffs")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Operation(summary = "Create Staff", description = "Create staff with role privileges")
    @PostMapping
    public ResponseEntity<Object> saveStaff(@RequestBody @Valid StaffCreateRequest staff) {
        Staff savedStaff = staffService.saveStaff(staff);
        String message = "Staff created successfully";
        StaffDetailsResponse response = new StaffDetailsResponse(message, savedStaff);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Staffs", description = "Get All staffs by applying page,size,createdBy,status filters")
    @GetMapping
    public ResponseEntity<Object> filterStaff(
            @RequestParam(required = false) UUID userRoleId,
            @RequestParam(required = false) UUID professionId,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Staff> filteredStaffs = staffService.filterStaff(userRoleId, professionId, createdBy, status, page-1, size);
        String message = "Staffs fetched successfully";
        StaffListResponse response = new StaffListResponse(message, filteredStaffs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get Staff Details", description = "Get all details of the associated staff")
    @GetMapping("{id}")
    public ResponseEntity<Object> getStaffDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Staff staff = staffService.findById(id);
        String message = "Staff details fetched successfully";
        StaffDetailsResponse response = new StaffDetailsResponse(message, staff);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Update Staff", description = "Update the staff exclude role privileges")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateStaff(@PathVariable UUID id ,@RequestBody @Valid StaffUpdateRequest request) throws ResourceNotFoundException {
        Staff updatedStaff  = staffService.updateStaff(id,request);
        String message = "Staff updated successfully";
        StaffDetailsResponse response = new StaffDetailsResponse(message,updatedStaff);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Archive Staff", description = "Archive the Staff -> Status=ARCHIVED and Disable Azure Ad user account")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveStaff(@PathVariable UUID id, @RequestBody StaffArchiveRequest request) throws ResourceNotFoundException {
        staffService.archiveStaff(id,request);
        String message = "Staff archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}

