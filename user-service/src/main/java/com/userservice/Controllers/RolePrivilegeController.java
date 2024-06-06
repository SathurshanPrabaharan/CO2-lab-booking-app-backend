package com.userservice.Controllers;


import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeArchiveRequest;
import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeCreateRequest;
import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeUpdateRequest;
import com.userservice.DTO.Response.ResponseMessage;
import com.userservice.DTO.Response.RolePrivilege.RolePrivilegeListResponse;
import com.userservice.DTO.Response.RolePrivilege.RolePrivilegeResponse;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.RolePrivilege;
import com.userservice.Services.RolePrivilegeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/role-privileges")
public class RolePrivilegeController {

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @PostMapping
    public ResponseEntity<Object> saveRolePrivilege(@RequestBody @Valid RolePrivilegeCreateRequest rolePrivilege){
        RolePrivilege savedRolePrivilege = rolePrivilegeService.saveRolePrivilege(rolePrivilege);
        String message = "Role Privilege created successfully";
        RolePrivilegeResponse response = new RolePrivilegeResponse(message, savedRolePrivilege);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




    @GetMapping
    public ResponseEntity<Object> getAllRolePrivileges(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status
    ){
        List<RolePrivilege> foundedRolePrivileges;

        if (createdBy == null && status == null){
           foundedRolePrivileges = rolePrivilegeService.getAllRolePrivileges(null,null);
        }else if(status == null){

            foundedRolePrivileges = rolePrivilegeService.getAllRolePrivileges(createdBy, null);
        }else{
            foundedRolePrivileges = rolePrivilegeService.getAllRolePrivileges(createdBy, STATUS.valueOf(status));
        }


        String message = "RolePrivileges fetched successfully";

        RolePrivilegeListResponse response;
        if(page==null && size==null) {
            response = new RolePrivilegeListResponse(message, foundedRolePrivileges);
        }else{
            response = new RolePrivilegeListResponse(message, foundedRolePrivileges, page, size);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> getRolePrivilegeDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        RolePrivilege rolePrivilege = rolePrivilegeService.findById(id);
        String message = "RolePrivilege details fetched successfully";
        RolePrivilegeResponse response = new RolePrivilegeResponse(message, rolePrivilege);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateRolePrivilege(@PathVariable UUID id ,@RequestBody @Valid RolePrivilegeUpdateRequest request) throws ResourceNotFoundException {
        RolePrivilege updatedRolePrivilege  = rolePrivilegeService.updateRolePrivilege(id,request);
        String message = "RolePrivilege updated successfully";
        RolePrivilegeResponse response = new RolePrivilegeResponse(message,updatedRolePrivilege);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveRolePrivilege(@PathVariable UUID id, @RequestBody RolePrivilegeArchiveRequest request) throws ResourceNotFoundException {
        rolePrivilegeService.archiveRolePrivilege(id,request);
        String message = "RolePrivilege archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}

