package com.userservice.DTO.Response.Profession;

import com.userservice.Models.Profession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessionResponse {

    private String message;
    private Profession data;

}