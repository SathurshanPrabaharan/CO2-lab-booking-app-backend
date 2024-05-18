package com.configurationservice.Services.Impls;



import com.configurationservice.DTO.Request.Course.CourseCreateRequest;
import com.configurationservice.DTO.Request.Course.CourseUpdateRequest;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Course;
import com.configurationservice.Repositories.CourseRepository;
import com.configurationservice.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;


    public CourseServiceImpl(CourseRepository courseRepository) {
        super();
        this.courseRepository = courseRepository;
    }

    @Override
    public Course saveCourse(CourseCreateRequest courseCreateRequest) {

        Course course = Course.builder()
                .code(courseCreateRequest.getCode())
                .name(courseCreateRequest.getName())
                .type(courseCreateRequest.getType())
                .departmentId(courseCreateRequest.getDepartmentId())
                .semester(courseCreateRequest.getSemester())
                .createdBy(courseCreateRequest.getCreatedBy())
                .status(STATUS.ACTIVE)
                .build();

        return  courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    @Override
    public List<Course> getAllCourses(UUID createdBy, STATUS status) {
        if (createdBy != null && status != null) {
            return courseRepository.findByCreatedByAndStatus(createdBy, status);
        } else if (createdBy != null) {
            return courseRepository.findByCreatedBy(createdBy);
        } else if (status != null) {
            return courseRepository.findByStatus(status);
        } else {
            return courseRepository.findAll();
        }
    }

    @Override
    public Course findById(UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.orElseThrow(() -> new ResourceNotFoundException("Course not found with id : " + id));
    }



    @Override
    public Course updateCourse(UUID id, CourseUpdateRequest courseUpdateRequest) {

        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id : " + id)
                );

        Course updatedCourse = Course.builder()
                .code(existingCourse.getCode())
                .name(courseUpdateRequest.getName())
                .type(courseUpdateRequest.getType())
                .departmentId(courseUpdateRequest.getDepartmentId())
                .semester(courseUpdateRequest.getSemester())
                .createdBy(existingCourse.getCreatedBy())
                .updatedBy(courseUpdateRequest.getUpdatedBy())
                .status(STATUS.ACTIVE)
                .build();

        return  courseRepository.save(updatedCourse);

    }


    @Override
    public void archiveCourse(UUID id) {

        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id : " + id)
                );
        if(existingCourse.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Course already archived");
        }

        Course archivedCourse = Course.builder()
                .code(existingCourse.getCode())
                .name(existingCourse.getName())
                .type(existingCourse.getType())
                .departmentId(existingCourse.getDepartmentId())
                .semester(existingCourse.getSemester())
                .createdBy(existingCourse.getCreatedBy())
                .updatedBy(existingCourse.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .build();

        courseRepository.save(archivedCourse);
    }



}
