package com.configurationservice.DTO.Response.Department;

import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaff;
import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaffSimple;
import com.configurationservice.Models.Department;
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
