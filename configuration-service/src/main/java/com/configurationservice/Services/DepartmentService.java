package com.configurationservice.Services;


import com.configurationservice.DTO.Request.Department.DepartmentCreateRequest;
import com.configurationservice.DTO.Request.Department.DepartmentUpdateRequest;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Department;

import java.util.List;
import java.util.UUID;


public interface DepartmentService {

    Department saveDepartment(DepartmentCreateRequest admin);

    List<Department> getAllDepartments();

    List<Department> getAllDepartments(UUID createdBy, STATUS status);

    Department findById(UUID id);

    Department updateDepartment(UUID id, DepartmentUpdateRequest departmentUpdateRequest);


    void archiveDepartment(UUID id);

}
