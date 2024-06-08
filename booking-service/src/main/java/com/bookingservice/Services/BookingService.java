package com.bookingservice.Services;


import com.bookingservice.DTO.Request.Booking.BookingArchiveRequest;
import com.bookingservice.DTO.Request.Booking.BookingCreateRequest;
import com.bookingservice.DTO.Request.Booking.BookingPatchRequest;
import com.bookingservice.DTO.Request.Booking.BookingUpdateRequest;
import com.bookingservice.Models.Booking;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface BookingService {

    Booking saveBooking(BookingCreateRequest booking);

    List<Booking> getAllBookings();

    Page<Booking> filterBooking(UUID courseId, LocalDate date,String bookingStatus, UUID createdByStaffId, UUID createdByAdminId, UUID approvedBy,UUID rejectedBy, String status,int page, int size);


    Booking findById(UUID id);

    Booking updateBooking(UUID id, BookingUpdateRequest bookingUpdateRequest);


    void archiveBooking(UUID id, BookingArchiveRequest request);

    Booking approveOrRejectBooking(UUID id, BookingPatchRequest request);

}
