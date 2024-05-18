package com.configurationservice.DTO.Response.Department;

import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Department;
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


    public DepartmentDetailsResponse(String message, Department foundedDepartment) {
        this.message = message;
        this.data = new ResponseDepartmentDetails(foundedDepartment);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseDepartmentDetails {
    private UUID id;
    private String name;
    private UUID HODId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;

    public ResponseDepartmentDetails(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.HODId = department.getHodId();
        this.createdAt = department.getCreatedAt();
        this.updatedAt = department.getUpdatedAt();
        this.createdBy = department.getCreatedBy();
        this.updatedBy = department.getUpdatedBy();
        this.status = department.getStatus();
    }
}
