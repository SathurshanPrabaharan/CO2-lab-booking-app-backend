package com.userservice.DTO.Response.Staff;


import com.userservice.Models.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffResponse {

    private String message;
    private Staff data;

}