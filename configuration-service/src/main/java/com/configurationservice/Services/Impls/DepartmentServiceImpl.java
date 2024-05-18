package com.configurationservice.Services.Impls;



import com.configurationservice.DTO.Request.Department.DepartmentCreateRequest;
import com.configurationservice.DTO.Request.Department.DepartmentUpdateRequest;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Department;
import com.configurationservice.Repositories.DepartmentRepository;
import com.configurationservice.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        super();
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department saveDepartment(DepartmentCreateRequest departmentCreateRequest) {

        Department department = Department.builder()
                .id(UUID.randomUUID())
                .name(departmentCreateRequest.getName())
                .createdBy(departmentCreateRequest.getCreatedBy())
                .hodId(departmentCreateRequest.getHodId())
                .status(STATUS.ACTIVE)
                .build();

        return  departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    @Override
    public List<Department> getAllDepartments(UUID createdBy, STATUS status) {
        if (createdBy != null && status != null) {
            return departmentRepository.findByCreatedByAndStatus(createdBy, status);
        } else if (createdBy != null) {
            return departmentRepository.findByCreatedBy(createdBy);
        } else if (status != null) {
            return departmentRepository.findByStatus(status);
        } else {
            return departmentRepository.findAll();
        }
    }

    @Override
    public Department findById(UUID id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.orElseThrow(() -> new ResourceNotFoundException("Department not found with id : " + id));
    }



    @Override
    public Department updateDepartment(UUID id, DepartmentUpdateRequest departmentUpdateRequest) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Department not found with id : " + id)
                );

        Department updatedDepartment = Department.builder()
                .id(existingDepartment.getId())
                .name(departmentUpdateRequest.getName())
                .hodId(departmentUpdateRequest.getHodId())
                .createdBy(existingDepartment.getCreatedBy())
                .createdAt(existingDepartment.getCreatedAt())
                .updatedBy(departmentUpdateRequest.getUpdatedBy())
                .status(STATUS.valueOf(departmentUpdateRequest.getStatus().toUpperCase()))
                .build();

        return  departmentRepository.save(updatedDepartment);

    }


    @Override
    public void archiveDepartment(UUID id) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Department not found with id : " + id)
                );
        if(existingDepartment.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Department already archived");
        }

        Department archivedDepartment = Department.builder()
                .id(existingDepartment.getId())
                .name(existingDepartment.getName())
                .hodId(existingDepartment.getHodId())
                .createdBy(existingDepartment.getCreatedBy())
                .createdAt(existingDepartment.getCreatedAt())
                .status(STATUS.ARCHIVED)
                .build();

        departmentRepository.save(archivedDepartment);
    }



}
