package com.bookingservice.Models.SupportModels;

import com.bookingservice.Enums.GENDER;
import com.bookingservice.Enums.STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

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

    @Column(name = "display_name",nullable = false)
    private String displayName;

    @Column
    private String mobile;

    @Column
    private GENDER gender;

    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_student_userRole"))
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "profession_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_student_profession"))
    private Profession profession;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_student_department"))
    private Department department;


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

}
