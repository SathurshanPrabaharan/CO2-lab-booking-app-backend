package com.configurationservice.DTO.Response.Department;

import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaff;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.SupportModels.Staff;
import com.configurationservice.Repositories.SupportRepositories.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentDetailsResponse {

    private String message;
    private ResponseDepartmentDetails data;

    public DepartmentDetailsResponse(String message, Department foundedDepartment, StaffRepository staffRepository) {
        this.message = message;
        this.data = new ResponseDepartmentDetails(foundedDepartment, staffRepository);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseDepartmentDetails {

    private UUID id;
    private String name;
    private ModifiedStaff hod = null;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;

    public ResponseDepartmentDetails(Department department, StaffRepository staffRepository) {
        this.id = department.getId();
        this.name = department.getName();
        this.createdAt = department.getCreatedAt();
        this.updatedAt = department.getUpdatedAt();
        this.createdBy = department.getCreatedBy();
        this.updatedBy = department.getUpdatedBy();
        this.status = department.getStatus();

        if (department.getHodId() != null) {
            Staff hod = staffRepository.findById(department.getHodId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("HOD not found with id: " + department.getHodId())
                    );
            this.hod=new ModifiedStaff(hod);
        }
    }
}
