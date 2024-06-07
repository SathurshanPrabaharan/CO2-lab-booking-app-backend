package com.bookingservice.DTO.Response.Booking;

import com.bookingservice.Models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingResponse {

    private String message;
    private Booking data;

}