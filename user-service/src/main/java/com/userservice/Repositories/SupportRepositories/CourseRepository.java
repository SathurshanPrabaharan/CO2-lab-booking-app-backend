package com.userservice.Repositories.SupportRepositories;


import com.userservice.Models.SupportModels.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}
