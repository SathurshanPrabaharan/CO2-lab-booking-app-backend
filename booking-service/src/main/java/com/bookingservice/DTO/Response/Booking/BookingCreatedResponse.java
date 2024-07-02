package com.bookingservice.DTO.Response.Booking;


import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedAdminSimple;
import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedCourse;
import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedStaffSimple;
import com.bookingservice.Enums.BOOKING_STATUS;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingCreatedResponse {

    private String message;
    private ResponseCreatedBooking data;


    public BookingCreatedResponse(String message, Booking foundedBooking) {
        this.message = message;
        this.data = new ResponseCreatedBooking(foundedBooking);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCreatedBooking {
    private UUID id;
    private String title;
    private String description;
    private String requirementDescription;
    private ModifiedCourse course;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BOOKING_STATUS bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ModifiedStaffSimple createdByStaff;
    private ModifiedAdminSimple createdByAdmin;
    private ModifiedStaffSimple updatedByStaff;
    private ModifiedAdminSimple updatedByAdmin;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private ModifiedAdminSimple approvedBy;
    private ModifiedAdminSimple rejectedBy;
    private STATUS status;
    private String userEmail;





    public ResponseCreatedBooking(Booking booking) {
        this.id = booking.getId();
        this.title = booking.getTitle();
        this.description = booking.getDescription();
        this.requirementDescription = booking.getRequirementDescription();
        this.date = booking.getDate();
        this.startTime = booking.getStartTime();
        this.endTime = booking.getEndTime();
        this.bookingStatus = booking.getBookingStatus();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt=booking.getUpdatedAt();
        this.approvedAt=booking.getApprovedAt();
        this.rejectedAt = booking.getRejectedAt();
        this.status = booking.getStatus();
        this.userEmail=booking.getUserEmail();


        // course
        if(booking.getCourse() != null){
            this.course = new ModifiedCourse(booking.getCourse());
        }


        // created by
        if(booking.getCreatedByStaff() != null){
            this.createdByStaff = new ModifiedStaffSimple(booking.getCreatedByStaff());
        }

        if(booking.getCreatedByAdmin() != null){
            this.createdByAdmin = new ModifiedAdminSimple(booking.getCreatedByAdmin());
        }



        // updated by
        if(booking.getUpdatedByStaff() != null){
            this.updatedByStaff = new ModifiedStaffSimple(booking.getUpdatedByStaff());
        }

        if(booking.getUpdatedByAdmin() != null){
            this.updatedByAdmin = new ModifiedAdminSimple(booking.getUpdatedByAdmin());
        }

        // approved by
        if(booking.getApprovedBy() != null){
            this.approvedBy = new ModifiedAdminSimple(booking.getApprovedBy());
        }

        // rejected by
        if(booking.getRejectedBy() != null){
            this.rejectedBy = new ModifiedAdminSimple(booking.getRejectedBy());
        }


    }
}
