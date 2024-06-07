package com.configurationservice.Services.Impls;



import com.configurationservice.DTO.Request.Department.DepartmentArchiveRequest;
import com.configurationservice.DTO.Request.Department.DepartmentCreateRequest;
import com.configurationservice.DTO.Request.Department.DepartmentUpdateRequest;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.SupportModels.Staff;
import com.configurationservice.Repositories.DepartmentRepository;
import com.configurationservice.Repositories.SupportRepositories.StaffRepository;
import com.configurationservice.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final StaffRepository staffRepository;


    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            StaffRepository staffRepository
    ) {
        super();
        this.departmentRepository = departmentRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public Department saveDepartment(DepartmentCreateRequest request) {

        Department department = Department.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .createdBy(request.getCreatedBy())
                .status(STATUS.ACTIVE)
                .build();

        // Set HOD if exists
        if (request.getHodId() != null) {
            Staff hod = staffRepository.findById(request.getHodId())
                    .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + request.getHodId()));
            department.setHod(hod);
        }

        return  departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    @Override
    public Page<Department> filterDepartment(UUID createdBy, String status, int page, int size) {
        // Fetch all departments ordered by createdAt in descending order
        List<Department> allDepartments = departmentRepository.findAllByOrderByCreatedAtDesc();

        // Apply filters
        List<Department> filteredDepartment = allDepartments.stream()
                .filter(department -> createdBy == null || department.getCreatedBy().equals(createdBy))
                .filter(department -> status == null || department.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        // Apply pagination
        int start = Math.min(page * size, filteredDepartment.size());
        int end = Math.min((page + 1) * size, filteredDepartment.size());
        List<Department> paginatedList = filteredDepartment.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), filteredDepartment.size());
    }

    @Override
    public Department findById(UUID id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.orElseThrow(() -> new ResourceNotFoundException("Department not found with id : " + id));
    }



    @Override
    public Department updateDepartment(UUID id, DepartmentUpdateRequest request) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Department not found with id : " + id)
                );

        Department updatedDepartment = Department.builder()
                .id(existingDepartment.getId())
                .name(request.getName())
                .createdBy(existingDepartment.getCreatedBy())
                .createdAt(existingDepartment.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.valueOf(request.getStatus().toUpperCase()))
                .build();

        // Set HOD if exists
        if (request.getHodId() != null) {
            Staff hod = staffRepository.findById(request.getHodId())
                    .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + request.getHodId()));
            updatedDepartment.setHod(hod);
        }

        return  departmentRepository.save(updatedDepartment);

    }


    @Override
    public void archiveDepartment(UUID id, DepartmentArchiveRequest request) {

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
                .hod(existingDepartment.getHod())
                .createdAt(existingDepartment.getCreatedAt())
                .createdBy(existingDepartment.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .build();

        departmentRepository.save(archivedDepartment);
    }



}
