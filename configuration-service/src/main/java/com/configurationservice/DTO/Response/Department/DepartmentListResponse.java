package com.configurationservice.DTO.Response.Department;

import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentListResponse {

    private String message;
    private ResponseDepartmentPaginatedList data;

    public DepartmentListResponse(String message, List<Department> foundedDepartment, int page, int size) {
        this.message = message;
        this.data = new ResponseDepartmentPaginatedList(foundedDepartment, page, size);
    }

    public DepartmentListResponse(String message, List<Department> foundedDepartment) {
        this.message = message;
        this.data = new ResponseDepartmentPaginatedList(foundedDepartment);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseDepartmentPaginatedList {
    private int total;
    private List<ResponseDepartmentList> results;

    public ResponseDepartmentPaginatedList(List<Department> results, Integer page, Integer size) {
        this.results = new ArrayList<>();

        if (results != null && !results.isEmpty()) {
            this.total = results.size();
            int startIndex = (page - 1) * size;
            int endIndex = Math.min(page * size, total);

            List<Department> paginatedAdmins;
            if (startIndex < total) {
                paginatedAdmins = results.subList(startIndex, endIndex);
            } else {
                paginatedAdmins = new ArrayList<>();
            }

            for (Department department : paginatedAdmins) {
                this.results.add(new ResponseDepartmentList(department));
            }
        } else {
            this.total = 0;
            this.results = new ArrayList<>();
        }
    }

    public ResponseDepartmentPaginatedList(List<Department> results) {
        this.total = results.size();
        this.results = new ArrayList<>();
        for (Department department : results) {
            this.results.add(new ResponseDepartmentList(department));
        }
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseDepartmentList {
    private UUID id;
    private String name;
    private UUID HODId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;

    public ResponseDepartmentList(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.HODId = department.getHodId();
        this.createdAt = department.getCreatedAt();
        this.updatedAt = department.getUpdatedAt();
        this.createdBy = department.getCreatedBy();
        this.updatedBy = department.getUpdatedBy();
        this.status = department.getStatus();   }
}
