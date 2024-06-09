package com.configurationservice.Services.Impls;


import com.configurationservice.DTO.Request.Course.CourseArchiveRequest;
import com.configurationservice.DTO.Request.Course.CourseCreateRequest;
import com.configurationservice.DTO.Request.Course.CourseUpdateRequest;
import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Exceptions.ResourceNotFoundException;
import com.configurationservice.Models.Course;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.SupportModels.Staff;
import com.configurationservice.Repositories.CourseRepository;
import com.configurationservice.Repositories.DepartmentRepository;
import com.configurationservice.Repositories.SupportRepositories.StaffRepository;
import com.configurationservice.Services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final StaffRepository staffRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;


    public CourseServiceImpl(CourseRepository courseRepository, StaffRepository staffRepository, DepartmentRepository departmentRepository) {
        super();
        this.courseRepository = courseRepository;
        this.staffRepository = staffRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Course saveCourse(CourseCreateRequest request) {
        Course course = Course.builder()
                .id(UUID.randomUUID())
                .code(request.getCode())
                .name(request.getName())
                .courseType(COURSE_TYPE.valueOf(request.getCourseType().toUpperCase()))
                .semester(request.getSemester())
                .createdBy(request.getCreatedBy())
                .status(STATUS.ACTIVE)
                .build();

        // Set Department if exist
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException ("Department not found with id: " + request.getDepartmentId()));
            course.setDepartment(department);
        }

        // Set Coordinator if exists
        if (request.getCoordinatorId() != null) {
            Staff coordinator = staffRepository.findById(request.getCoordinatorId())
                    .orElseThrow(() -> new IllegalArgumentException ("Coordinator not found with id: " + request.getCoordinatorId()));
            course.setCoordinator(coordinator);
        }

        // Set Responsible Staffs if exists
        if (request.getResponsibleStaffIds() != null) {
            Set<UUID> responsibleStaffIds = request.getResponsibleStaffIds();
            List<Staff> responsibleStaffList = staffRepository.findAllById(responsibleStaffIds);

            // Validate if all responsible staff IDs were found
            if (responsibleStaffList.size() != responsibleStaffIds.size()) {
                throw new IllegalArgumentException ("One or more Responsible Staff IDs not found");
            }

            // Update Course's responsible staffs
            course.getResponsibleStaffs().addAll(responsibleStaffList);
        }

        return courseRepository.save(course);
    }


    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    @Override
    public Page<Course> filterCourse(UUID departmentId, String courseType, Short semester, UUID responsibleStaffId, UUID createdBy, String status, int page, int size) {
        // Fetch all courses ordered by createdAt in descending order
        List<Course> allCourses = courseRepository.findAllByOrderByCreatedAtDesc();

        // Apply filters
        List<Course> filteredCourse = allCourses.stream()
                .filter(course -> departmentId == null || (course.getDepartment() != null && course.getDepartment().getId().equals(departmentId)))
                .filter(course -> courseType == null || course.getCourseType().toString().equalsIgnoreCase(courseType))
                .filter(course -> semester == null ||  course.getSemester().equals(semester))
                .filter(course -> createdBy == null || course.getCreatedBy().equals(createdBy))
                .filter(course -> status == null || course.getStatus().toString().equalsIgnoreCase(status))
                .filter(course -> responsibleStaffId == null || course.getResponsibleStaffs().stream().anyMatch(staff -> staff.getId().equals(responsibleStaffId)))
                .collect(Collectors.toList());

        // Apply pagination
        int start = Math.min(page * size, filteredCourse.size());
        int end = Math.min((page + 1) * size, filteredCourse.size());
        List<Course> paginatedList = filteredCourse.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), filteredCourse.size());
    }

    @Override
    public Course findById(UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Course foundedCourse = courseOptional.orElseThrow(() -> new IllegalArgumentException("Course not found with id : " + id));
        Set<Staff> responsibleStaffs= courseRepository.findResponsibleStaffsByCourseId(id);
        foundedCourse.setResponsibleStaffs(responsibleStaffs);
        return foundedCourse;
    }



    @Override
    public Course updateCourse(UUID id, CourseUpdateRequest request) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id : " + id));

        // Update mutable fields from the request
        existingCourse.setName(request.getName());
        existingCourse.setCourseType(COURSE_TYPE.valueOf(request.getCourseType().toUpperCase()));
        existingCourse.setUpdatedBy(request.getUpdatedBy());
        existingCourse.setStatus(STATUS.valueOf(request.getStatus().toUpperCase()));

        // Set semester if it has been provided
        if (request.getSemester() != 0) {
            existingCourse.setSemester(request.getSemester());
        }

        // Set Department if exist
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + request.getDepartmentId()));
            existingCourse.setDepartment(department);
        }

        // Set Coordinator if exists
        if (request.getCoordinatorId() != null) {
            Staff coordinator = staffRepository.findById(request.getCoordinatorId())
                    .orElseThrow(() -> new IllegalArgumentException("Coordinator not found with id: " + request.getCoordinatorId()));
            existingCourse.setCoordinator(coordinator);
        }

        // Delete existing entries from staff_courses table related to the course
        courseRepository.deleteStaffCoursesByCourseId(existingCourse.getId());

        // Update Responsible Staffs if provided
        if (request.getResponsibleStaffIds() != null && !request.getResponsibleStaffIds().isEmpty()) {
            List<Staff> responsibleStaffList = staffRepository.findAllById(request.getResponsibleStaffIds());

            // Find missing IDs
            Set<UUID> foundIds = responsibleStaffList.stream()
                    .map(Staff::getId)
                    .collect(Collectors.toSet());
            Set<UUID> missingIds = request.getResponsibleStaffIds().stream()
                    .filter(staffId -> !foundIds.contains(staffId))
                    .collect(Collectors.toSet());

            // Validate if all responsible staff IDs were found
            if (!missingIds.isEmpty()) {
                throw new IllegalArgumentException("One or more Responsible Staff IDs not found: " + missingIds);
            }

            // Set the new responsible staffs
            existingCourse.setResponsibleStaffs(new HashSet<>(responsibleStaffList));
        } else {
            // If no responsible staff IDs are provided, clear the responsible staffs
            existingCourse.getResponsibleStaffs().clear();
        }

        return courseRepository.save(existingCourse); // Save the updated course with new responsible staff
    }







    @Override
    public void archiveCourse(UUID id, CourseArchiveRequest request) {

        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id : " + id)
                );
        if(existingCourse.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Course already archived");
        }

        Course archivedCourse = Course.builder()
                .id(existingCourse.getId())
                .code(existingCourse.getCode())
                .name(existingCourse.getName())
                .courseType(existingCourse.getCourseType())
                .department(existingCourse.getDepartment())
                .semester(existingCourse.getSemester())
                .coordinator(existingCourse.getCoordinator())
                .responsibleStaffs(existingCourse.getResponsibleStaffs())
                .createdAt(existingCourse.getCreatedAt())
                .createdBy(existingCourse.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .build();

        courseRepository.save(archivedCourse);
    }


}
