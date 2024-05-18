package com.userservice.Controllers;

import com.userservice.DTO.Request.Admin.AdminCreateRequest;
import com.userservice.DTO.Request.Admin.AdminUpdateRequest;
import com.userservice.DTO.Response.Admin.AdminDetailsResponse;
import com.userservice.DTO.Response.Admin.AdminListResponse;
import com.userservice.DTO.Response.Admin.AdminResponse;
import com.userservice.DTO.Response.Admin.AdminResponseMessage;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Admin;
import com.userservice.Services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Object> saveEmployee(@RequestBody @Valid AdminCreateRequest admin){
        Admin savedAdmin = adminService.saveAdmin(admin);
        String message = "Admin created successfully";
        AdminResponse response = new AdminResponse(message, savedAdmin);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<Object> getAllAdmins(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status
    ){
        List<Admin> foundedAdmins;

        if (name == null && createdBy == null && status == null){
           foundedAdmins = adminService.getAllAdmins(null,null,null);
        }else if(status == null){

            foundedAdmins = adminService.getAllAdmins(name, createdBy, null);
        }else{
            foundedAdmins = adminService.getAllAdmins(name, createdBy, STATUS.valueOf(status));
        }


        String message = "Admins fetched successfully";

        AdminListResponse response;
        if(page==null && size==null) {
            response = new AdminListResponse(message, foundedAdmins);
        }else{
            response = new AdminListResponse(message, foundedAdmins, page, size);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> getAdminDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Admin admin = adminService.findById(id);
        String message = "Admin details fetched successfully";
        AdminDetailsResponse response = new AdminDetailsResponse(message, admin);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateAdmin(@PathVariable UUID id ,@RequestBody @Valid AdminUpdateRequest adminUpdateRequest) throws ResourceNotFoundException {
        Admin updatedAdmin  = adminService.updateAdmin(id,adminUpdateRequest);
        String message = "Admin updated successfully";
        AdminDetailsResponse response = new AdminDetailsResponse(message,updatedAdmin);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveAdmin(@PathVariable UUID id) throws ResourceNotFoundException {
        adminService.archiveAdmin(id);
        String message = "Admin archived successfully";
        AdminResponseMessage response = new AdminResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
