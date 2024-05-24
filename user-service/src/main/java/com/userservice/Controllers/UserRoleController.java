package com.userservice.Controllers;


import com.userservice.DTO.Request.UserRole.UserRoleArchiveRequest;
import com.userservice.DTO.Request.UserRole.UserRoleCreateRequest;
import com.userservice.DTO.Request.UserRole.UserRolePatchRequest;
import com.userservice.DTO.Request.UserRole.UserRoleUpdateRequest;
import com.userservice.DTO.Response.ResponseMessage;
import com.userservice.DTO.Response.RolePrivilege.RolePrivilegeListResponse;
import com.userservice.DTO.Response.UserRole.UserRoleListResponse;
import com.userservice.DTO.Response.UserRole.UserRoleResponse;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.RolePrivilege;
import com.userservice.Models.UserRole;
import com.userservice.Services.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/user-roles")
@Tag(name = "User Role Controller", description = "Endpoints for user roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Operation(summary = "Create User Role", description = "Create user role with role privileges")
    @PostMapping
    public ResponseEntity<Object> saveUserRole(@RequestBody @Valid UserRoleCreateRequest userRole) {
        UserRole savedUserRole = userRoleService.saveUserRole(userRole);
        String message = "Role Privilege created successfully";
        UserRoleResponse response = new UserRoleResponse(message, savedUserRole);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get User Roles", description = "Get All user roles by applying page,size,createdBy,status filters")
    @GetMapping
    public ResponseEntity<Object> getAllUserRoles(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status
    ){
        List<UserRole> foundedUserRoles;

        if (createdBy == null && status == null){
            foundedUserRoles = userRoleService.getAllUserRoles(null,null);
        }else if(status == null){

            foundedUserRoles = userRoleService.getAllUserRoles(createdBy, null);
        }else{
            foundedUserRoles = userRoleService.getAllUserRoles(createdBy, STATUS.valueOf(status));
        }


        String message = "UserRoles fetched successfully";

        UserRoleListResponse response;
        if(page==null && size==null) {
            response = new UserRoleListResponse(message, foundedUserRoles);
        }else{
            response = new UserRoleListResponse(message, foundedUserRoles, page, size);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get User Role Details", description = "Get all details of the associated user role")
    @GetMapping("{id}")
    public ResponseEntity<Object> getUserRoleDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        UserRole userRole = userRoleService.findById(id);
        String message = "UserRole details fetched successfully";
        UserRoleResponse response = new UserRoleResponse(message, userRole);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Update User Role", description = "Update the user role exclude role privileges")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateUserRole(@PathVariable UUID id ,@RequestBody @Valid UserRoleUpdateRequest request) throws ResourceNotFoundException {
        UserRole updatedUserRole  = userRoleService.updateUserRole(id,request);
        String message = "UserRole updated successfully";
        UserRoleResponse response = new UserRoleResponse(message,updatedUserRole);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Archive User Role", description = "Archive the User Role -> Status=ARCHIVED")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveUserRole(@PathVariable UUID id, @RequestBody UserRoleArchiveRequest request) throws ResourceNotFoundException {
        userRoleService.archiveUserRole(id,request);
        String message = "UserRole archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "Add Privileges", description = "Add Role Privileges to User Role")
    @PatchMapping("{id}/role-privileges")
    public ResponseEntity<Object> addUserRolePrivileges(@PathVariable UUID id ,@RequestBody @Valid UserRolePatchRequest request) throws ResourceNotFoundException {
        UserRole updatedUserRole  = userRoleService.addUserRolePrivileges(id,request);
        String message = "UserRole privileges updated successfully";
        // Convert the PersistentSet to List<RolePrivilege>
        List<RolePrivilege> privilegeList = new ArrayList<>(updatedUserRole.getPrivilegeSet());
        RolePrivilegeListResponse response = new RolePrivilegeListResponse(message, privilegeList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "Get Privileges", description = "Get User Role Privileges")
    @GetMapping("{id}/role-privileges")
    public ResponseEntity<Object> getUserRolePrivileges(
            @PathVariable UUID id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) throws ResourceNotFoundException {
        UserRole userRole = userRoleService.findById(id);
        String message = "Privileges of UserRole fetched successfully";
        // Convert the PersistentSet to List<RolePrivilege>
        List<RolePrivilege> privilegeList = new ArrayList<>(userRole.getPrivilegeSet());
        RolePrivilegeListResponse response = new RolePrivilegeListResponse(message, privilegeList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "Remove Privilege", description = "Remove Role Privileges from User Role Not delete them")
    @DeleteMapping("{id}/role-privileges/{privilegeId}")
    public ResponseEntity<Object> removeUserRolePrivilege(
            @PathVariable UUID id,
            @PathVariable UUID privilegeId,
            @RequestBody UserRoleArchiveRequest request
    ) {
        RolePrivilege removedPrivilege = userRoleService.removeUserRolePrivilege(id, privilegeId,request);
        String message = " '"+removedPrivilege.getKey()+"' removed successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}

