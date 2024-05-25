package com.userservice.Models;

import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Models.SupportModels.Course;
import com.userservice.Models.SupportModels.Profession;
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

@Entity
@Table(name="students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true,nullable = false)
    private UUID objectId;

    @Column( name = "first_name",nullable = false)
    private String firstName;

    @Column( name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "display_name")
    private String displayName;

    @Column
    private String mobile;

    @Column
    private GENDER gender;

    @ManyToOne
    @JoinColumn(name = "userRole_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_student_userRole"))
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_student_profession"))
    private Profession profession;

    @Column
    private Short semester;

    @Column(name = "reg_num")
    private String regNum;

    //save and merge together
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @Builder.Default
    private Set<Course> currentCourses = new HashSet<>();

    // email from organization
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

    @Column
    private STATUS status;


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

}
