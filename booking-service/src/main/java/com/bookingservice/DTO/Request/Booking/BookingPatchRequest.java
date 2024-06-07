package com.bookingservice.DTO.Request.Booking;

import com.bookingservice.Enums.BOOKING_STATUS;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingPatchRequest {

    @NotNull(message = "Invalid bookingStatus: bookingStatus cannot be null")
    private String bookingStatus;

    private String rejectReason;

    @NotNull(message = "Invalid updatedByAdminId: updatedByAdminId cannot be null")
    private UUID updatedByAdminId;

    public void validate() {
        // Validate bookingStatus
        if (!bookingStatus.equalsIgnoreCase(BOOKING_STATUS.APPROVED.name()) &&
                !bookingStatus.equalsIgnoreCase(BOOKING_STATUS.REJECTED.name())) {
            throw new IllegalArgumentException("Invalid bookingStatus: must be either APPROVED or REJECTED");
        }

        // Validate rejectReason if bookingStatus is REJECTED
        if (bookingStatus.equalsIgnoreCase(BOOKING_STATUS.REJECTED.name()) && (rejectReason == null || rejectReason.trim().isEmpty())) {
            throw new IllegalArgumentException("Invalid rejectReason: cannot be null or empty when bookingStatus is REJECTED");
        }
    }
}
