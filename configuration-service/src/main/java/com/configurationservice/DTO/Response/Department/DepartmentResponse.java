package com.configurationservice.DTO.Response.Department;

import com.configurationservice.Models.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentResponse {

    private String message;
    private Department data;

}