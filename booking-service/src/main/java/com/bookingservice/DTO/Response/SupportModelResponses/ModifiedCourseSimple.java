package com.bookingservice.DTO.Response.SupportModelResponses;


import com.bookingservice.Enums.COURSE_TYPE;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Models.SupportModels.Course;
import com.bookingservice.Models.SupportModels.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedCourseSimple {
    private UUID id;
    private String code;
    private String name;
    private COURSE_TYPE courseType;
    private String department;
    private Short semester;


    public ModifiedCourseSimple(Course course){
        this.id=course.getId();
        this.code= course.getCode();
        this.name=course.getName();
        this.courseType=course.getCourseType();
        this.semester=course.getSemester();


        if(course.getDepartment() != null){
            this.department = course.getDepartment().getName();
        }

    }
}
