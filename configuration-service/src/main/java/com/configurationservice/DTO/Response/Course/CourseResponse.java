package com.configurationservice.DTO.Response.Course;

import com.configurationservice.Models.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponse {

    private String message;
    private Course data;

}