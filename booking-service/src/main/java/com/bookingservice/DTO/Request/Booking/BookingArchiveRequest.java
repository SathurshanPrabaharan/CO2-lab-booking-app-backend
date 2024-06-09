package com.bookingservice.DTO.Request.Booking;

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
public class BookingArchiveRequest {

    @NotNull(message = "Invalid updatedByAdminId: updatedByAdminId cannot be null")
    private UUID updatedByAdminId;


}
