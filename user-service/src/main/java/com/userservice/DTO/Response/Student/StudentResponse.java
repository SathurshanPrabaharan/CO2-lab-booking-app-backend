package com.userservice.DTO.Response.Student;


import com.userservice.Models.Staff;
import com.userservice.Models.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentResponse {

    private String message;
    private Student data;

}