package com.bookingservice.Services.Impls;


import com.bookingservice.DTO.Request.Booking.BookingArchiveRequest;
import com.bookingservice.DTO.Request.Booking.BookingCreateRequest;
import com.bookingservice.DTO.Request.Booking.BookingPatchRequest;
import com.bookingservice.DTO.Request.Booking.BookingUpdateRequest;
import com.bookingservice.Enums.BOOKING_STATUS;
import com.bookingservice.Enums.COURSE_TYPE;
import com.bookingservice.Enums.STATUS;
import com.bookingservice.Exceptions.ResourceNotFoundException;
import com.bookingservice.Models.Booking;
import com.bookingservice.Models.SupportModels.Admin;
import com.bookingservice.Models.SupportModels.Course;
import com.bookingservice.Models.SupportModels.Staff;
import com.bookingservice.Repositories.BookingRepository;
import com.bookingservice.Repositories.SupportRepositories.AdminRepository;
import com.bookingservice.Repositories.SupportRepositories.CourseRepository;
import com.bookingservice.Repositories.SupportRepositories.StaffRepository;
import com.bookingservice.Services.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private final StaffRepository staffRepository;

    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final CourseRepository courseRepository;




    public BookingServiceImpl(
            BookingRepository bookingRepository,
            StaffRepository staffRepository,
            AdminRepository adminRepository,
            CourseRepository courseRepository
    ) {
        super();
        this.bookingRepository = bookingRepository;
        this.staffRepository = staffRepository;
        this.adminRepository = adminRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Booking saveBooking(BookingCreateRequest request) {

        //validate the request
        request.validate();

        // Check for overlapping bookings
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                request.getDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new IllegalArgumentException("Booking time overlaps with an existing booking.");
        }

        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .title(request.getTitle())
                .description(request.getDescription())
                .requirementDescription(request.getRequirementDescription())
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(STATUS.ACTIVE)
                .build();


        // Set Course if exist
        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException ("Course not found with id: " + request.getCourseId()));
            booking.setCourse(course);
        }

        // if CreatedByStaff exists
        if (request.getCreatedByStaffId() != null) {
            Staff createdByStaff = staffRepository.findById(request.getCreatedByStaffId())
                    .orElseThrow(() -> new IllegalArgumentException ("CreatedByStaff not found with id: " + request.getCreatedByStaffId()));
            booking.setCreatedByStaff(createdByStaff);
            booking.setBookingStatus(BOOKING_STATUS.PENDING);
        }

        // if  CreatedByAdmin exists
        if (request.getCreatedByAdminId() != null) {
            Admin createdByAdmin = adminRepository.findById(request.getCreatedByAdminId())
                    .orElseThrow(() -> new IllegalArgumentException ("CreatedByAdmin not found with id: " + request.getCreatedByAdminId()));
            booking.setCreatedByAdmin(createdByAdmin);
            booking.setBookingStatus(BOOKING_STATUS.APPROVED);
            booking.setApprovedBy(createdByAdmin);
            booking.setApprovedAt(LocalDateTime.now());
        }


        return bookingRepository.save(booking);
    }




    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    @Override
    public Page<Booking> filterBooking(UUID courseId, LocalDate date, String bookingStatus, UUID createdByStaffId, UUID createdByAdminId, UUID approvedBy, UUID rejectedBy, String status, int page, int size) {
        // Fetch all bookings ordered by date and startTime
        List<Booking> allBookings = bookingRepository.findAllOrderByDateAndStartTime();

        // Apply filters
        List<Booking> filteredBooking = allBookings.stream()
                .filter(booking -> courseId == null || (booking.getCourse() != null && booking.getCourse().getId().equals(courseId)))
                .filter(booking -> date == null || booking.getDate().equals(date))
                .filter(booking -> bookingStatus == null || booking.getBookingStatus().toString().equalsIgnoreCase(bookingStatus))
                .filter(booking -> createdByStaffId == null || (booking.getCreatedByStaff() != null && booking.getCreatedByStaff().getId().equals(createdByStaffId)))
                .filter(booking -> createdByAdminId == null || (booking.getCreatedByAdmin() != null && booking.getCreatedByAdmin().getId().equals(createdByAdminId)))
                .filter(booking -> approvedBy == null || (booking.getApprovedBy() != null && booking.getApprovedBy().getId().equals(approvedBy)))
                .filter(booking -> rejectedBy == null || (booking.getRejectedBy() != null && booking.getRejectedBy().getId().equals(rejectedBy)))
                .filter(booking -> status == null || booking.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        // Apply pagination
        int start = Math.min(page * size, filteredBooking.size());
        int end = Math.min((page + 1) * size, filteredBooking.size());
        List<Booking> paginatedList = filteredBooking.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), filteredBooking.size());
    }

    @Override
    public Booking findById(UUID id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        Booking foundedBooking = bookingOptional.orElseThrow(
                () -> new IllegalArgumentException("Booking not found with id : " + id)
        );
        return foundedBooking;
    }



    @Override
    @Transactional
    public Booking updateBooking(UUID id, BookingUpdateRequest request) {

        // Validate the request
        request.validate();

        // Get Existing Booking
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id : " + id));

        // Check for overlapping bookings (excluding the current booking being updated)
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                        request.getDate(),
                        request.getStartTime(),
                        request.getEndTime()
                ).stream()
                .filter(booking -> !booking.getId().equals(id))
                .toList();

        if (!overlappingBookings.isEmpty()) {
            throw new IllegalArgumentException("Booking time overlaps with an existing booking.");
        }

        // Update mutable fields from the request
        existingBooking.setTitle(request.getTitle());
        existingBooking.setDescription(request.getDescription());
        existingBooking.setRequirementDescription(request.getRequirementDescription());
        existingBooking.setDate(request.getDate());
        existingBooking.setStartTime(request.getStartTime());
        existingBooking.setEndTime(request.getEndTime());
//        existingBooking.setBookingStatus(BOOKING_STATUS.valueOf(request.getBookingStatus().toUpperCase()));


        // Set Course if exist
        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + request.getCourseId()));
            existingBooking.setCourse(course);
        }

        // If updatedByStaff exists
        if (request.getUpdatedByStaffId() != null) {
            Staff updatedByStaff = staffRepository.findById(request.getUpdatedByStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("UpdatedByStaff not found with id: " + request.getUpdatedByStaffId()));
            existingBooking.setUpdatedByStaff(updatedByStaff);
        }

        // If updatedByAdmin exists
        if (request.getUpdatedByAdminId() != null) {
            Admin updatedByAdmin = adminRepository.findById(request.getUpdatedByAdminId())
                    .orElseThrow(() -> new IllegalArgumentException("UpdatedByAdmin not found with id: " + request.getUpdatedByAdminId()));
            existingBooking.setUpdatedByAdmin(updatedByAdmin);
        }

        return bookingRepository.save(existingBooking); // Save the updated booking
    }







    @Override
    public void archiveBooking(UUID id, BookingArchiveRequest request) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Booking not found with id : " + id)
                );
        if(existingBooking.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Booking already archived");
        }

        // set updatedByAdmin
        Admin updatedByAdmin = adminRepository.findById(request.getUpdatedByAdminId())
                .orElseThrow(() -> new IllegalArgumentException("UpdatedByAdmin not found with id: " + request.getUpdatedByAdminId()));
        existingBooking.setUpdatedByAdmin(updatedByAdmin);


        existingBooking.setStatus(STATUS.ARCHIVED);


        bookingRepository.save(existingBooking);
    }

    @Override
    @Transactional
    public Booking approveOrRejectBooking(UUID id, BookingPatchRequest request) {
        // Validate the request
        request.validate();

        // Get Existing Booking
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id : " + id));

        // check the status
        if(existingBooking.getStatus()==STATUS.ARCHIVED){
            throw new IllegalArgumentException("Invalid : Booking in ARCHIVED status");
        }

        // Check if the booking status is already APPROVED or REJECTED
        if (existingBooking.getBookingStatus() == BOOKING_STATUS.APPROVED ||
                existingBooking.getBookingStatus() == BOOKING_STATUS.REJECTED) {
            throw new IllegalArgumentException("Booking status cannot be changed once it is APPROVED or REJECTED.");
        }


        // Update booking status and other fields based on the request
        BOOKING_STATUS status = BOOKING_STATUS.valueOf(request.getBookingStatus().toUpperCase());
        existingBooking.setBookingStatus(status);

        Admin updatedByAdmin = adminRepository.findById(request.getUpdatedByAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + request.getUpdatedByAdminId()));
        existingBooking.setUpdatedByAdmin(updatedByAdmin);

        if (status == BOOKING_STATUS.APPROVED) {
            existingBooking.setApprovedAt(LocalDateTime.now());
            existingBooking.setApprovedBy(updatedByAdmin);
            //sent mail

        } else if (status == BOOKING_STATUS.REJECTED) {
            existingBooking.setRejectedAt(LocalDateTime.now());
            existingBooking.setRejectedBy(updatedByAdmin);
            existingBooking.setRejectReason(request.getRejectReason());
            existingBooking.setStatus(STATUS.INACTIVE);
            //sent mail
        }

        return bookingRepository.save(existingBooking);
    }



}
