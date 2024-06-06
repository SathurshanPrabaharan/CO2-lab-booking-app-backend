package com.configurationservice.Repositories;

import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {


    List<Course> findByCreatedByAndStatus(UUID createdBy, STATUS status);

    List<Course> findByCreatedBy(UUID createdBy);

    List<Course> findByStatus(STATUS status);
}
