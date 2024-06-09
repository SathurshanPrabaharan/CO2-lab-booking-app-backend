package com.configurationservice.Repositories;

import com.configurationservice.Models.Course;
import com.configurationservice.Models.SupportModels.Staff;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findAllByOrderByCreatedAtDesc();

    @Query("SELECT c.responsibleStaffs FROM Course c WHERE c.id = :courseId")
    Set<Staff> findResponsibleStaffsByCourseId(@Param("courseId") UUID courseId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM staff_courses WHERE course_id = ?1", nativeQuery = true)
    void deleteStaffCoursesByCourseId(UUID courseId);

}


