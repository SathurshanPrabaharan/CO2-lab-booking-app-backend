package com.userservice.DTO.Response.Course;


import com.userservice.Enums.COURSE_TYPE;
import com.userservice.Models.SupportModels.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedCourse {
    private UUID id;
    private String name;
    private String code;
    private int semester;
    private String department;
    private COURSE_TYPE courseType;

    public ModifiedCourse(Course course){
        this.id=course.getId();
        this.name=course.getName();
        this.code=course.getCode();
        this.semester=course.getSemester();
        this.department=course.getDepartment().getName();
        this.courseType=course.getType();

    }
}
