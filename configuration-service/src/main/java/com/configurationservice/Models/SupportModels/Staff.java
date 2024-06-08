package com.configurationservice.Models.SupportModels;


import com.configurationservice.Enums.GENDER;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.Course;
import com.configurationservice.Models.Department;
import com.configurationservice.Models.Profession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name="staffs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "object_id",unique = true,nullable = false)
    private UUID objectId;

    @Column( name = "first_name",nullable = false)
    private String firstName;

    @Column( name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column
    private String mobile;

    @Column
    private GENDER gender;

    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_staff_userRole"))
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_staff_profession"))
    private Profession profession;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_student_department"))
    private Department department;

    //save and merge together
    @ManyToMany(mappedBy = "responsibleStaffs", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY,targetEntity = Course.class)
    @Builder.Default
    private Set<Course> responsibleCourses = new HashSet<>();

//    @ManyToMany(mappedBy = "responsibleStaffs", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @Builder.Default
//    private Set<Course> responsibleCourses = new HashSet<>();


    // Mail ID from organization
    @Column(name = "user_principal_name", nullable = false,unique = true)
    private String userPrincipalName;

    @Column
    private String contact_email;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "is_initial_logged")
    @Builder.Default
    private Boolean isInitalLogged = false;

    @Column(name = "verify_token")
    private String verifyToken;

    @Column(name = "token_issued_at")
    private LocalDateTime tokenIssuedAt;

    @Column
    @Builder.Default
    private Boolean accountEnabled = false;

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

    @Column
    private STATUS status;


    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", objectId=" + objectId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", userRole=" + userRole +
                ", profession=" + profession +
                ", department=" + department +
                ", responsibleCourses=" + responsibleCourses.stream().map(Course::getId).toList() +
                ", userPrincipalName='" + userPrincipalName + '\'' +
                ", contact_email='" + contact_email + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", isInitalLogged=" + isInitalLogged +
                ", verifyToken='" + verifyToken + '\'' +
                ", tokenIssuedAt=" + tokenIssuedAt +
                ", accountEnabled=" + accountEnabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", status=" + status +
                '}';
    }


}
