package com.configurationservice.DTO.Response.Course;

import com.configurationservice.DTO.Response.Department.ModifiedDepartment;
import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaff;
import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import com.configurationservice.Models.SupportModels.Staff;
import com.configurationservice.Repositories.SupportRepositories.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDetailsResponse {

    private String message;
    private ResponseCourseDetails data;

    public CourseDetailsResponse(String message, Course foundedCourse, StaffRepository staffRepository) {
        this.message = message;
        this.data = new ResponseCourseDetails(foundedCourse, staffRepository);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCourseDetails {

    private UUID id;
    private String code;
    private String name;
    private COURSE_TYPE courseType;
    private ModifiedDepartment department;
    private Short semester;
    private ModifiedStaff coordinator;
    private Set<ModifiedStaff> responsibleStaffs = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;

    public ResponseCourseDetails(Course course, StaffRepository staffRepository) {
        this.id = course.getId();
        this.code = course.getCode();
        this.name = course.getName();
        this.courseType = course.getCourseType();
        this.semester = course.getSemester();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
        this.createdBy = course.getCreatedBy();
        this.updatedBy = course.getUpdatedBy();
        this.status = course.getStatus();

        if (course.getDepartment() != null) {
            this.department = new ModifiedDepartment(course.getDepartment());
        }

        if (course.getCoordinator() != null) {
            this.coordinator = new ModifiedStaff(course.getCoordinator());
        }

        Set<Staff> temp = course.getResponsibleStaffs();
        if (temp != null) {
            temp.stream()
                    .map(ModifiedStaff::new)
                    .forEach(this.responsibleStaffs::add);
        }




    }
}
