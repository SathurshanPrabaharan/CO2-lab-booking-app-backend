package com.bookingservice.DTO.Response.Booking;


import com.bookingservice.DTO.Response.SupportModelResponses.ModifiedCourseSimple;
import com.bookingservice.Enums.BOOKING_STATUS;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedBooking {
    private UUID id;
    private String title;
    private String description;
    private String requirementDescription;
    private ModifiedCourseSimple course;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BOOKING_STATUS bookingStatus;
    private STATUS status;


    public ModifiedBooking(Booking booking){
        this.id=booking.getId();
        this.title= booking.getTitle();
        this.description= booking.getDescription();
        this.requirementDescription = booking.getRequirementDescription();
        this.date = booking.getDate();
        this.startTime=booking.getStartTime();
        this.endTime=booking.getEndTime();
        this.bookingStatus=booking.getBookingStatus();
        this.status= booking.getStatus();


        if(booking.getCourse() != null){
            this.course = new ModifiedCourseSimple(booking.getCourse());
        }


    }
}
