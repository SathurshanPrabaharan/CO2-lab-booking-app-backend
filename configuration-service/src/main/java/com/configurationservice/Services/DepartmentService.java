package com.configurationservice.Services;


import com.configurationservice.DTO.Request.Department.DepartmentArchiveRequest;
import com.configurationservice.DTO.Request.Department.DepartmentCreateRequest;
import com.configurationservice.DTO.Request.Department.DepartmentUpdateRequest;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Department;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface DepartmentService {

    Department saveDepartment(DepartmentCreateRequest admin);

    List<Department> getAllDepartments();

    Page<Department> filterDepartment(UUID createdBy, String status, int page, int size);

    Department findById(UUID id);

    Department updateDepartment(UUID id, DepartmentUpdateRequest departmentUpdateRequest);


    void archiveDepartment(UUID id, DepartmentArchiveRequest request);

}
