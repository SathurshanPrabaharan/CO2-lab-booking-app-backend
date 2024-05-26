package com.configurationservice.DTO.Response.Department;

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
    private UUID hodId;

    public ModifiedDepartment(Department department){
        this.id=department.getId();
        this.name=department.getName();
        this.hodId=department.getHodId();

    }
}
