package com.userservice.Services;


import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeArchiveRequest;
import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeCreateRequest;
import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Models.RolePrivilege;

import java.util.List;
import java.util.UUID;


public interface RolePrivilegeService {

    RolePrivilege saveRolePrivilege(RolePrivilegeCreateRequest rolePrivilege);

    List<RolePrivilege> getAllRolePrivileges();

    List<RolePrivilege> getAllRolePrivileges( UUID createdBy, STATUS status);

    RolePrivilege findById(UUID id);

    RolePrivilege updateRolePrivilege(UUID id, RolePrivilegeUpdateRequest request);


    void archiveRolePrivilege(UUID id, RolePrivilegeArchiveRequest request);

}
