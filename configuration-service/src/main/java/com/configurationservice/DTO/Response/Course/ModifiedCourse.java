package com.configurationservice.DTO.Response.Course;

import com.configurationservice.DTO.Response.SupportModelResponses.ModifiedStaffSimple;
import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.SupportModels.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedCourse {
    private UUID id;
    private String code;
    private String name;
    private COURSE_TYPE courseType;
    private String department;
    private Short semester;
    private ModifiedStaffSimple coordinator;
    private Set<ModifiedStaffSimple> responsibleStaffs= new HashSet<>();
    private STATUS status;

    public ModifiedCourse(Course course){
        this.id=course.getId();
        this.code= course.getCode();
        this.name=course.getName();
        this.courseType=course.getCourseType();
        this.semester=course.getSemester();

        if(course.getCoordinator() != null){
            this.coordinator = new ModifiedStaffSimple(course.getCoordinator());
        }

        if(course.getDepartment() != null){
            this.department = course.getDepartment().getName();
        }

        Set<Staff> responsibleStaffs = course.getResponsibleStaffs();
        if (responsibleStaffs != null) {
            for (Staff staff : responsibleStaffs) {
                this.responsibleStaffs.add(new ModifiedStaffSimple(staff));
            }
        }


    }
}
