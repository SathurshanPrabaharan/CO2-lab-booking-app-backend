package com.userservice.Repositories;

import com.userservice.Enums.STATUS;
import com.userservice.Models.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    Optional<UserRole> findByKey(String key);

    List<UserRole> findByCreatedByAndStatus(UUID createdBy, STATUS status);

    List<UserRole> findByCreatedBy(UUID createdBy);

    List<UserRole> findByStatus(STATUS status);


}
