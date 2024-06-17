package com.bookingservice.DTO.Response.Booking;


import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedAdmin;
import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedCourse;
import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedStaff;
import com.bookingservice.Enums.BOOKING_STATUS;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDetailsResponse {

    private String message;
    private ResponseBookingDetails data;

    public BookingDetailsResponse(String message, Booking foundedBooking) {
        this.message = message;
        this.data = new ResponseBookingDetails(foundedBooking);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseBookingDetails {

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
    private ModifiedStaff createdByStaff;
    private ModifiedAdmin createdByAdmin;
    private ModifiedStaff updatedByStaff;
    private ModifiedAdmin updatedByAdmin;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private String rejectReason;
    private ModifiedAdmin approvedBy;
    private ModifiedAdmin rejectedBy;
    private STATUS status;


    public ResponseBookingDetails(Booking booking) {
        this.id = booking.getId();
        this.title = booking.getTitle();
        this.description = booking.getDescription();
        this.requirementDescription = booking.getRequirementDescription();
        this.date = booking.getDate();
        this.startTime=booking.getStartTime();
        this.endTime=booking.getEndTime();
        this.bookingStatus=booking.getBookingStatus();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt = booking.getUpdatedAt();
        this.approvedAt = booking.getApprovedAt();
        this.rejectedAt = booking.getRejectedAt();
        this.rejectReason = booking.getRejectReason();
        this.status = booking.getStatus();


        if (booking.getCourse() != null) {
            this.course = new ModifiedCourse(booking.getCourse());
        }

        // Created By
        if (booking.getCreatedByStaff() != null) {
            this.createdByStaff = new ModifiedStaff(booking.getCreatedByStaff());
        }

        if(booking.getCreatedByAdmin() != null){
            this.createdByAdmin = new ModifiedAdmin(booking.getCreatedByAdmin());
        }

        // Updated By
        if (booking.getUpdatedByStaff() != null) {
            this.updatedByStaff = new ModifiedStaff(booking.getUpdatedByStaff());
        }

        if(booking.getUpdatedByAdmin() != null){
            this.updatedByAdmin = new ModifiedAdmin(booking.getUpdatedByAdmin());
        }

        // Approved By
        if(booking.getApprovedBy() != null){
            this.approvedBy = new ModifiedAdmin(booking.getApprovedBy());
        }

        // Rejected By
        if(booking.getRejectedBy() != null){
            this.rejectedBy = new ModifiedAdmin(booking.getRejectedBy());
        }




    }
}
