package com.userservice.Services;


import com.userservice.DTO.Request.Admin.AdminCreateRequest;
import com.userservice.DTO.Request.Admin.AdminUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    Admin saveAdmin(AdminCreateRequest admin);
    List<Admin> getAllAdmins();

    List<Admin> getAllAdmins(String name, UUID createdBy, STATUS status);

    Admin findById(UUID id);

    Admin updateAdmin(UUID id, AdminUpdateRequest adminUpdateRequest);


    void archiveAdmin(UUID id);
}
