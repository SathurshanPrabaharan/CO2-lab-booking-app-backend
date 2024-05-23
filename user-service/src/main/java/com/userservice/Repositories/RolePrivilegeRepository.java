package com.userservice.Repositories;


import com.userservice.Enums.STATUS;
import com.userservice.Models.Admin;
import com.userservice.Models.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, UUID> {

    Optional<RolePrivilege> findByKey(String key);

    List<RolePrivilege> findByCreatedByAndStatus(UUID createdBy, STATUS status);

    List<RolePrivilege> findByCreatedBy(UUID createdBy);

    List<RolePrivilege> findByStatus(STATUS status);
}
