package com.configurationservice.Models;

import com.configurationservice.Enums.COURSE_TYPE;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.SupportModels.Staff;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false,unique = true)
    private String code;

    @Column( nullable = false)
    private String name;

    @Column( nullable = false)
    private COURSE_TYPE courseType;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_course_department"))
    private Department department;

    @Column( nullable = false)
    @Min(value = 1, message = "Semester must be greater than or equal to 1")
    @Max(value = 8, message = "Semester must be less than or equal to 8")
    private Short semester;

    @ManyToOne
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_course_coordinator"))
    private Staff coordinator;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Staff.class)
    @JoinTable(
            name = "staff_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    @Builder.Default
    private Set<Staff> responsibleStaffs = new HashSet<>();


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column( nullable = false)
    private STATUS status;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", courseType=" + courseType +
                ", department=" + department +
                ", semester=" + semester +
                ", coordinator=" + coordinator +
                ", responsibleStaffs=" + responsibleStaffs.stream().map(Staff::getId).toList() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", status=" + status +
                '}';
    }


}
