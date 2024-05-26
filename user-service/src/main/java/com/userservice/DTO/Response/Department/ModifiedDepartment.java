package com.userservice.DTO.Response.Department;


import com.userservice.Enums.COURSE_TYPE;
import com.userservice.Models.SupportModels.Course;
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
    private UUID hodId;

    public ModifiedDepartment(Department department){
        this.id=department.getId();
        this.name=department.getName();
        this.hodId=department.getHodId();

    }
}
