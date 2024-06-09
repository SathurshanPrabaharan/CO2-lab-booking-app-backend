package com.configurationservice.DTO.Response.Department;


import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaffSimple;
import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.SupportModels.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentCreatedResponse {

    private String message;
    private ResponseCreatedDepartment data;


    public DepartmentCreatedResponse(String message, Department foundedDepartment) {
        this.message = message;
        this.data = new ResponseCreatedDepartment(foundedDepartment);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCreatedDepartment {
    private UUID id;
    private String name;
    private ModifiedStaffSimple hod;
    private STATUS status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public ResponseCreatedDepartment(Department department) {
        this.id = department.getId();
        this.name=department.getName();
        this.status = department.getStatus();
        this.createdAt = department.getCreatedAt();
        this.updatedAt = department.getUpdatedAt();
        this.createdBy = department.getCreatedBy();
        this.updatedBy = department.getUpdatedBy();

        if(department.getHod() != null){
            this.hod = new ModifiedStaffSimple(department.getHod());
        }




    }
}
