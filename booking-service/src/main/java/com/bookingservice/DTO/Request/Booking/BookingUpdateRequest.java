package com.bookingservice.DTO.Request.Booking;


import com.bookingservice.Enums.BOOKING_STATUS;
import com.bookingservice.Validations.ValidStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingUpdateRequest {



    @NotNull(message = "Invalid title: title cannot be null")
    @Size(min = 1, max = 255)
    private String title;

    private String description;

    private String requirementDescription;

    @NotNull(message = "Invalid courseId: courseId cannot be null")
    private UUID courseId;

    @NotNull(message = "Invalid date: date cannot be null")
    @FutureOrPresent(message = "The booking date must be present or in the future.")
    private LocalDate date;

    @NotNull(message = "Invalid startTime: startTime cannot be null")
    private LocalTime startTime;

    @NotNull(message = "Invalid endTime: endTime cannot be null")
    private LocalTime endTime;

    private UUID updatedByStaffId;

    private UUID updatedByAdminId;

    @NotNull(message = "Invalid status: Status cannot be null")
    @ValidStatus(message = "Invalid status: Status must be one of ACTIVE, INACTIVE, or ARCHIVED")
    private String status;

    // Add validation logic in the DTO itself
    public void validate() {
        validateTimeSlot();
        validateCreator();
    }

    private void validateTimeSlot() {
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(16, 0);
        if (startTime.isBefore(start) || endTime.isAfter(end) || !isHalfHourInterval(startTime) || !isHalfHourInterval(endTime)) {
            throw new IllegalArgumentException("Booking time must be between 08:00 AM and 04:00 PM and in 30-minute intervals.");
        }
        if (startTime.isAfter(endTime) || !startTime.plusMinutes(30).isBefore(endTime.plusMinutes(1))) {
            throw new IllegalArgumentException("Invalid booking time range.");
        }
    }

    private boolean isHalfHourInterval(LocalTime time) {
        return time.getMinute() % 30 == 0;
    }

    private void validateCreator() {
        if (updatedByStaffId == null && updatedByAdminId == null) {
            throw new IllegalArgumentException("Booking must be updated by either a staff or an admin.");
        }
        if (updatedByStaffId != null && updatedByAdminId != null) {
            throw new IllegalArgumentException("Booking cannot be updated by both a staff and an admin.");
        }
    }


}
