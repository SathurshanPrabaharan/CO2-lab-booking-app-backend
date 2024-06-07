package com.userservice.DTO.Response.SupportModelResponses;


import com.userservice.Models.SupportModels.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedDepartment {
    private UUID id;
    private String name;
    private ModifiedStaffSimple hod;

    public ModifiedDepartment(Department department){
        this.id=department.getId();
        this.name=department.getName();

        if(department.getHod() != null){
            this.hod = new ModifiedStaffSimple(department.getHod());
        }

    }
}
