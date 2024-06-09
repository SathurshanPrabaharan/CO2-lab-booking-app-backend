package com.configurationservice.DTO.Response.Course;

import com.configurationservice.DTO.Response.Course.ModifiedCourse;
import com.configurationservice.Models.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseListResponse {

    private String message;
    private ResultCourse data;


    public CourseListResponse(String message, Page<Course> filteredCourses) {
        this.message = message;
        this.data = new ResultCourse(filteredCourses);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultCourse {
    private long total;
    private List<ModifiedCourse> results;

    ResultCourse(Page<Course> results) {
        this.total = results.getTotalElements();

        List<ModifiedCourse> resultModifiedCourses = new ArrayList<>();
        for(Course currentCourse: results.getContent()){
            ModifiedCourse temp = new ModifiedCourse(currentCourse);
            resultModifiedCourses.add(temp);
        }

        this.results = resultModifiedCourses;

    }

}

