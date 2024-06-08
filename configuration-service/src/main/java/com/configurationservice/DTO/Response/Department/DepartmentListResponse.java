package com.configurationservice.DTO.Response.Department;

import com.configurationservice.Models.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentListResponse {

    private String message;
    private ResultDepartment data;


    public DepartmentListResponse(String message, Page<Department> filteredDepartments) {
        this.message = message;
        this.data = new ResultDepartment(filteredDepartments);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultDepartment {
    private long total;
    private List<ModifiedDepartment> results;

    ResultDepartment(Page<Department> results) {
        this.total = results.getTotalElements();

        List<ModifiedDepartment> resultModifiedDepartments = new ArrayList<>();
        for(Department currentDepartment: results.getContent()){
            ModifiedDepartment temp = new ModifiedDepartment(currentDepartment);
            resultModifiedDepartments.add(temp);
        }

        this.results = resultModifiedDepartments;

    }

}

