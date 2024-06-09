package com.userservice.DTO.Response.Student;

import com.userservice.DTO.Response.Student.ModifiedStudent;
import com.userservice.Models.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentListResponse {

    private String message;
    private ResultStudent data;


    public StudentListResponse(String message, Page<Student> filteredStudents) {
        this.message = message;
        this.data = new ResultStudent(filteredStudents);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultStudent {
    private long total;
    private List<ModifiedStudent> results;

    ResultStudent(Page<Student> results) {
        this.total = results.getTotalElements();

        List<ModifiedStudent> resultModifiedStudents = new ArrayList<>();
        for(Student currentStudent: results.getContent()){
            ModifiedStudent temp = new ModifiedStudent(currentStudent);
            resultModifiedStudents.add(temp);
        }

        this.results = resultModifiedStudents;

    }

}

