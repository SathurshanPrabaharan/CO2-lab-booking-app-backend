package com.userservice.Repositories;

import com.userservice.Enums.STATUS;
import com.userservice.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    List<Admin> findByNameAndCreatedByAndStatus(String name, UUID createdBy, STATUS status);

    List<Admin> findByNameAndCreatedBy(String name, UUID createdBy);

    List<Admin> findByNameAndStatus(String name, STATUS status);

    List<Admin> findByCreatedByAndStatus(UUID createdBy, STATUS status);

    List<Admin> findByName(String name);

    List<Admin> findByCreatedBy(UUID createdBy);

    List<Admin> findByStatus(STATUS status);
}
