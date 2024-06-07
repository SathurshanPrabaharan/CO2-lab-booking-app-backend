package com.bookingservice.Models;

import com.bookingservice.Enums.BOOKING_STATUS;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Models.SupportModels.Admin;
import com.bookingservice.Models.SupportModels.Course;
import com.bookingservice.Models.SupportModels.Staff;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name="bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(name = "requirement_description")
    private String requirementDescription;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_course"))
    private Course course;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private BOOKING_STATUS bookingStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by_staff", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_created_staff"))
    private Staff createdByStaff;

    @ManyToOne
    @JoinColumn(name = "created_by_admin", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_created_admin"))
    private Admin createdByAdmin;

    @ManyToOne
    @JoinColumn(name = "updated_by_staff", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_updated_staff"))
    private Staff updatedByStaff;

    @ManyToOne
    @JoinColumn(name = "updated_by_admin", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_booking_updated_admin"))
    private Admin updatedByAdmin;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "reject_reason")
    private String rejectReason;

    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_approved_booking_admin"))
    private Admin approvedBy;

    @ManyToOne
    @JoinColumn(name = "rejected_by", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_rejected_booking_admin"))
    private Admin rejectedBy;

    @Column( nullable = false)
    private STATUS status;

}
