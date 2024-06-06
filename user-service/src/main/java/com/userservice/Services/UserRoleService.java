package com.userservice.Services;


import com.userservice.DTO.Request.UserRole.UserRoleArchiveRequest;
import com.userservice.DTO.Request.UserRole.UserRoleCreateRequest;
import com.userservice.DTO.Request.UserRole.UserRolePatchRequest;
import com.userservice.DTO.Request.UserRole.UserRoleUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Models.RolePrivilege;
import com.userservice.Models.UserRole;

import java.util.List;
import java.util.UUID;


public interface UserRoleService {

    UserRole saveUserRole(UserRoleCreateRequest userRole);

    List<UserRole> getAllUserRoles();

    List<UserRole> getAllUserRoles( UUID createdBy, STATUS status);


    UserRole findById(UUID id);

    UserRole updateUserRole(UUID id, UserRoleUpdateRequest request);

    UserRole addUserRolePrivileges(UUID id, UserRolePatchRequest request);


    void archiveUserRole(UUID id, UserRoleArchiveRequest request);

    RolePrivilege removeUserRolePrivilege(UUID userRoleId, UUID privilegeId, UserRoleArchiveRequest request);
}
