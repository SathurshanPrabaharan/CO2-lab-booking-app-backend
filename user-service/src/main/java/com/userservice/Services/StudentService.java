package com.userservice.Services;


import com.userservice.DTO.Request.Student.StudentArchiveRequest;
import com.userservice.DTO.Request.Student.StudentCreateRequest;
import com.userservice.DTO.Request.Student.StudentUpdateRequest;
import com.userservice.Models.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface StudentService {

    Student saveStudent(StudentCreateRequest student);

    List<Student> getAllStudents();


    Page<Student> filterStudent(UUID userRoleId, UUID professionId, UUID departmentId, UUID courseId, String gender, Short semester, String status, UUID createdBy,int page, int size);

    Student findById(UUID id);

    Student findByObjectId(UUID id);

    Student updateStudent(UUID id, StudentUpdateRequest request);


    void archiveStudent(UUID id, StudentArchiveRequest request);

}
