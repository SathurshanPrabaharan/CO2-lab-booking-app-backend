package com.bookingservice.DTO.Response.Booking;


import com.bookingservice.Models.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingListResponse {

    private String message;
    private ResultBooking data;


    public BookingListResponse(String message, Page<Booking> filteredBookings) {
        this.message = message;
        this.data = new ResultBooking(filteredBookings);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultBooking {
    private long total;
    private List<ModifiedBooking> results;

    ResultBooking(Page<Booking> results) {
        this.total = results.getTotalElements();

        List<ModifiedBooking> resultModifiedBookings = new ArrayList<>();
        for(Booking currentBooking: results.getContent()){
            ModifiedBooking temp = new ModifiedBooking(currentBooking);
            resultModifiedBookings.add(temp);
        }

        this.results = resultModifiedBookings;

    }

}

