package com.configurationservice.Repositories;

import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {


    List<Department> findByCreatedByAndStatus(UUID createdBy, STATUS status);

    List<Department> findByCreatedBy(UUID createdBy);

    List<Department> findByStatus(STATUS status);
}
