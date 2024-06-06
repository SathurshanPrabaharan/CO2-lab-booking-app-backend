package com.userservice.DTO.Response.Admin;

import com.userservice.Models.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminResponse {

    private String message;
    private Admin data;

}