package com.userservice.Services;


import com.userservice.DTO.Request.Admin.AdminArchiveRequest;
import com.userservice.DTO.Request.Admin.AdminCreateRequest;
import com.userservice.DTO.Request.Admin.AdminUpdateRequest;
import com.userservice.Models.Admin;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface AdminService {

    Admin saveAdmin(AdminCreateRequest admin);

    List<Admin> getAllAdmins();


    Page<Admin> filterAdmin(UUID userRoleId, UUID professionId, UUID departmentId,  String gender, String status, UUID createdBy,int page, int size);

    Admin findById(UUID id);

    Admin findByObjectId(UUID id);

    Admin updateAdmin(UUID id, AdminUpdateRequest request);


    void archiveAdmin(UUID id, AdminArchiveRequest request);

}
