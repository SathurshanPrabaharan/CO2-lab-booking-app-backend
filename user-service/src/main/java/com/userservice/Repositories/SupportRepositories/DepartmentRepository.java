package com.userservice.Repositories.SupportRepositories;


import com.userservice.Models.SupportModels.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}
