package com.bookingservice.Controllers;


import com.bookingservice.DTO.Request.Booking.BookingArchiveRequest;
import com.bookingservice.DTO.Request.Booking.BookingCreateRequest;
import com.bookingservice.DTO.Request.Booking.BookingPatchRequest;
import com.bookingservice.DTO.Request.Booking.BookingUpdateRequest;
import com.bookingservice.DTO.Response.Booking.BookingCreatedResponse;
import com.bookingservice.DTO.Response.Booking.BookingDetailsResponse;
import com.bookingservice.DTO.Response.Booking.BookingListResponse;
import com.bookingservice.DTO.Response.Booking.BookingResponse;
import com.bookingservice.DTO.Response.ResponseMessage;
import com.bookingservice.Exceptions.ResourceNotFoundException;
import com.bookingservice.Models.Booking;
import com.bookingservice.Repositories.SupportRepositories.StaffRepository;
import com.bookingservice.Services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/bookings/valid-bookings")
@Tag(name = "Booking Controller", description = "Endpoints for bookings")
@CrossOrigin("http://localhost:5173")
//@CrossOrigin("http://192.168.52.120:5173/")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private StaffRepository staffRepository;

    @Operation(summary = "Create booking", description = "Create the booking in the faculty")
    @PostMapping
    public ResponseEntity<Object> saveBooking(@RequestBody @Valid BookingCreateRequest bookingCreateRequest){
        Booking savedBooking = bookingService.saveBooking(bookingCreateRequest);
        String message = "Booking created successfully";
        BookingCreatedResponse response = new BookingCreatedResponse(message, savedBooking);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Bookings", description = "Get All bookings by applying page,size,createdBy,status filters")
    @GetMapping
    public ResponseEntity<Object> filterBooking(
            @RequestParam(required = false) UUID courseId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String bookingStatus,
            @RequestParam(required = false) UUID createdByStaffId,
            @RequestParam(required = false) UUID createdByAdminId,
            @RequestParam(required = false) UUID approvedBy,
            @RequestParam(required = false) UUID rejectedBy,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ) {

        Page<Booking> filteredBookings = bookingService.filterBooking(courseId, date, bookingStatus, createdByStaffId, createdByAdminId, approvedBy,rejectedBy, status,page-1, size);
        String message = "Bookings fetched successfully";
        BookingListResponse response = new BookingListResponse(message, filteredBookings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get Booking Details", description = "Get all details of the booking")
    @GetMapping("{id}")
    public ResponseEntity<Object> getBookingDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Booking booking = bookingService.findById(id);
        String message = "Booking details fetched successfully";
        BookingDetailsResponse response = new BookingDetailsResponse(message, booking);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



    @Operation(summary = "Update Booking", description = "Update the booking")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable UUID id ,@RequestBody @Valid BookingUpdateRequest bookingUpdateRequest) throws ResourceNotFoundException {
        Booking updatedBooking  = bookingService.updateBooking(id,bookingUpdateRequest);
        String message = "Booking updated successfully";
        BookingDetailsResponse response = new BookingDetailsResponse(message,updatedBooking);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary = "Archive Booking", description = "Archive the booking -> Status=ARCHIVED")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveBooking(@PathVariable UUID id,@RequestBody BookingArchiveRequest request) throws ResourceNotFoundException {
        bookingService.archiveBooking(id,request);
        String message = "Booking archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * for approving or rejecting a booking
     *
     */
    @Operation(summary = "Approve or Reject Booking", description = "Approve or Reject Booking")
    @PatchMapping("{id}")
    public ResponseEntity<Object> approveOrRejectBooking(@PathVariable UUID id ,@RequestBody @Valid BookingPatchRequest request) throws ResourceNotFoundException {
        Booking updatedBooking  = bookingService.approveOrRejectBooking(id,request);
        String message = "Booking updated successfully";
        BookingCreatedResponse response = new BookingCreatedResponse(message,updatedBooking);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}
