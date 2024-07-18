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
import com.bookingservice.Models.NotificationMessage;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    //logger instance
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final StaffRepository staffRepository;
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final CourseRepository courseRepository;

    //sending messages to Kafka topics in a Spring Boot application
    //only messages of type NotificationMessage are sent
    @Autowired
    private KafkaTemplate<String, NotificationMessage> kafkaTemplate;

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

    // service for approve or reject the booking
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

        //used as the message payload that will be sent to a Kafka topic using kafka template
        NotificationMessage message = new NotificationMessage();

        if (status == BOOKING_STATUS.APPROVED) {
            existingBooking.setApprovedAt(LocalDateTime.now());
            existingBooking.setApprovedBy(updatedByAdmin);
            //This sets the recipient's email address to the email associated with the booking
            if(existingBooking.getCreatedByStaff()!= null)
            {
                message.setTo(existingBooking.getCreatedByStaff().getContact_email());
            }
            else
            {
                message.setTo(existingBooking.getCreatedByAdmin().getContact_email());
            }
            message.setSubject("Booking Approved");
            message.setBody(generateApprovalEmailBody(existingBooking));


        } else if (status == BOOKING_STATUS.REJECTED) {
            existingBooking.setRejectedAt(LocalDateTime.now());
            existingBooking.setRejectedBy(updatedByAdmin);
            existingBooking.setRejectReason(request.getRejectReason());
            existingBooking.setStatus(STATUS.INACTIVE);
            //sent mail
            if(existingBooking.getCreatedByStaff()!= null)
            {
                message.setTo(existingBooking.getCreatedByStaff().getContact_email());
            }
            else
            {
                message.setTo(existingBooking.getCreatedByAdmin().getContact_email());
            }
            message.setSubject("Booking Rejected");
            message.setBody(generateRejectionEmailBody(existingBooking, request.getRejectReason()));

        }

        bookingRepository.save(existingBooking);

        //informational message indicating that a notification message is being sent to Kafka
        logger.info("Sending message to Kafka: {}", message);
        /**
         * Publish message to Kafka
         *  sends the NotificationMessage(message) to the booking-notifications topic in Kafka
         */
        kafkaTemplate.send("booking-notifications", message);
        logger.info("Message sent to Kafka topic booking-notifications");

        return existingBooking;

    }

    private String generateApprovalEmailBody(Booking booking) {
        return "<!doctype html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>email-temp</title>" +
                "<style media=\"all\" type=\"text/css\">" +
                "body {font-family: Helvetica, sans-serif;-webkit-font-smoothing: antialiased;font-size: 16px;line-height: 1.3;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}" +
                "table {border-collapse: separate;mso-table-lspace: 0pt;mso-table-rspace: 0pt;width: 100%;}" +
                "table td {font-family: Helvetica, sans-serif;font-size: 16px;vertical-align: top;}" +
                "body {background-color: #f4f5f6;margin: 0;padding: 0;}" +
                ".body {background-color: #f4f5f6;width: 100%;}" +
                ".container {margin: 0 auto !important;max-width: 600px;padding: 0;padding-top: 24px;width: 600px;}" +
                ".content {box-sizing: border-box;display: block;margin: 0 auto;max-width: 600px;padding: 0;}" +
                ".main {background: #ffffff;border: 1px solid #eaebed;border-radius: 16px;width: 100%;}" +
                ".wrapper {box-sizing: border-box;padding: 24px;}" +
                ".footer {clear: both;padding-top: 24px;text-align: center;width: 100%;}" +
                ".footer td,.footer p,.footer span,.footer a {color: #9a9ea6;font-size: 16px;text-align: center;}" +
                "p {font-family: Helvetica, sans-serif;font-size: 16px;font-weight: normal;margin: 0;margin-bottom: 16px;}" +
                "a {color: #0867ec;text-decoration: underline;}" +
                ".btn {box-sizing: border-box;min-width: 100% !important;width: 100%;}" +
                ".btn > tbody > tr > td {padding-bottom: 16px;}" +
                ".btn table {width: auto;}" +
                ".btn table td {background-color: #ffffff;border-radius: 4px;text-align: center;}" +
                ".btn a {background-color: #ffffff;border: solid 2px #28a745;border-radius: 4px;box-sizing: border-box;color: #28a745;cursor: pointer;display: inline-block;font-size: 16px;font-weight: bold;margin: 0;padding: 12px 24px;text-decoration: none;text-transform: capitalize;}" +
                ".btn-primary table td {background-color: #28a745;}" +
                ".btn-primary a {background-color: #28a745;border-color: #28a745;color: #ffffff;}" +
                "@media all {.btn-primary table td:hover {background-color: #218838 !important;}.btn-primary a:hover {background-color: #218838 !important;border-color: #218838 !important;}}" +
                ".last {margin-bottom: 0;}" +
                ".first {margin-top: 0;}" +
                ".align-center {text-align: center;}" +
                ".align-right {text-align: right;}" +
                ".align-left {text-align: left;}" +
                ".text-link {color: #0867ec !important;text-decoration: underline !important;}" +
                ".clear {clear: both;}" +
                ".mt0 {margin-top: 0;}" +
                ".mb0 {margin-bottom: 0;}" +
                ".preheader {color: transparent;display: none;height: 0;max-height: 0;max-width: 0;opacity: 0;overflow: hidden;mso-hide: all;visibility: hidden;width: 0;}" +
                ".powered-by a {text-decoration: none;}" +
                "@media only screen and (max-width: 640px) {.main p,.main td,.main span {font-size: 16px !important;}.wrapper {padding: 8px !important;}.content {padding: 0 !important;}.container {padding: 0 !important;padding-top: 8px !important;width: 100% !important;}.main {border-left-width: 0 !important;border-radius: 0 !important;border-right-width: 0 !important;}.btn table {max-width: 100% !important;width: 100% !important;}.btn a {font-size: 16px !important;max-width: 100% !important;width: 100% !important;}}" +
                "@media all {.ExternalClass {width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {line-height: 100%;}.apple-link a {color: inherit !important;font-family: inherit !important;font-size: inherit !important;font-weight: inherit !important;line-height: inherit !important;text-decoration: none !important;}#MessageViewBody a {color: inherit;text-decoration: none;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">" +
                "<tr>" +
                "<td>&nbsp;</td>" +
                "<td class=\"container\">" +
                "<div class=\"content\">" +
                "<span class=\"preheader\">This is preheader text. Some clients will show this text as a preview.</span>" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"main\">" +
                "<tr>" +
                "<td class=\"wrapper\">" +
                "<p>Hi "+booking.getCreatedByStaff().getFirstName()+",</p>" +
                "<p>We are pleased to inform you that your lab booking request has been approved by the admin.</p>" +
                "<p>Course : "+booking.getCourse().getCode()+" - "+booking.getCourse().getName()+"</p>"+
                "<p>Date : "+booking.getDate()+"</p>"+
                "<p>Time : "+booking.getStartTime()+" - " + booking.getEndTime()+"</p>"+
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">" +
                "<tbody>" +
                "<tr>" +
                "<td align=\"left\">" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td> <a href=\"#\" target=\"_blank\">APPROVED</a> </td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "<p>Thank you for your patience and cooperation.</p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "<div class=\"footer\">" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tr>" +
                "<td class=\"content-block\">" +
                "<span class=\"apple-link\">University of Jaffna | Faculty of Engineering</span>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</td>" +
                "<td>&nbsp;</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";

    }

    private String generateRejectionEmailBody(Booking booking, String rejectReason) {
        return "<!doctype html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<title>email-temp</title>" +
                "<style media=\"all\" type=\"text/css\">" +
                "body {font-family: Helvetica, sans-serif;-webkit-font-smoothing: antialiased;font-size: 16px;line-height: 1.3;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}" +
                "table {border-collapse: separate;mso-table-lspace: 0pt;mso-table-rspace: 0pt;width: 100%;}" +
                "table td {font-family: Helvetica, sans-serif;font-size: 16px;vertical-align: top;}" +
                "body {background-color: #f4f5f6;margin: 0;padding: 0;}" +
                ".body {background-color: #f4f5f6;width: 100%;}" +
                ".container {margin: 0 auto !important;max-width: 600px;padding: 0;padding-top: 24px;width: 600px;}" +
                ".content {box-sizing: border-box;display: block;margin: 0 auto;max-width: 600px;padding: 0;}" +
                ".main {background: #ffffff;border: 1px solid #eaebed;border-radius: 16px;width: 100%;}" +
                ".wrapper {box-sizing: border-box;padding: 24px;}" +
                ".footer {clear: both;padding-top: 24px;text-align: center;width: 100%;}" +
                ".footer td,.footer p,.footer span,.footer a {color: #9a9ea6;font-size: 16px;text-align: center;}" +
                "p {font-family: Helvetica, sans-serif;font-size: 16px;font-weight: normal;margin: 0;margin-bottom: 16px;}" +
                "a {color: #0867ec;text-decoration: underline;}" +
                ".btn {box-sizing: border-box;min-width: 100% !important;width: 100%;}" +
                ".btn > tbody > tr > td {padding-bottom: 16px;}" +
                ".btn table {width: auto;}" +
                ".btn table td {background-color: #ffffff;border-radius: 4px;text-align: center;}" +
                ".btn a {background-color: #ffffff;border: solid 2px #ec0867;border-radius: 4px;box-sizing: border-box;color: #ec0867;cursor: pointer;display: inline-block;font-size: 16px;font-weight: bold;margin: 0;padding: 12px 24px;text-decoration: none;text-transform: capitalize;}" +
                ".btn-primary table td {background-color: #ec0867;}" +
                ".btn-primary a {background-color: #ec0867;border-color: #ec0867;color: #ffffff;}" +
                "@media all {.btn-primary table td:hover {background-color: #ec0867 !important;}.btn-primary a:hover {background-color: #ec0867 !important;border-color: #ec0867 !important;}}" +
                ".last {margin-bottom: 0;}" +
                ".first {margin-top: 0;}" +
                ".align-center {text-align: center;}" +
                ".align-right {text-align: right;}" +
                ".align-left {text-align: left;}" +
                ".text-link {color: #0867ec !important;text-decoration: underline !important;}" +
                ".clear {clear: both;}" +
                ".mt0 {margin-top: 0;}" +
                ".mb0 {margin-bottom: 0;}" +
                ".preheader {color: transparent;display: none;height: 0;max-height: 0;max-width: 0;opacity: 0;overflow: hidden;mso-hide: all;visibility: hidden;width: 0;}" +
                ".powered-by a {text-decoration: none;}" +
                "@media only screen and (max-width: 640px) {.main p,.main td,.main span {font-size: 16px !important;}.wrapper {padding: 8px !important;}.content {padding: 0 !important;}.container {padding: 0 !important;padding-top: 8px !important;width: 100% !important;}.main {border-left-width: 0 !important;border-radius: 0 !important;border-right-width: 0 !important;}.btn table {max-width: 100% !important;width: 100% !important;}.btn a {font-size: 16px !important;max-width: 100% !important;width: 100% !important;}}" +
                "@media all {.ExternalClass {width: 100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {line-height: 100%;}.apple-link a {color: inherit !important;font-family: inherit !important;font-size: inherit !important;font-weight: inherit !important;line-height: inherit !important;text-decoration: none !important;}#MessageViewBody a {color: inherit;text-decoration: none;font-size: inherit;font-family: inherit;font-weight: inherit;line-height: inherit;}}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">" +
                "<tr>" +
                "<td>&nbsp;</td>" +
                "<td class=\"container\">" +
                "<div class=\"content\">" +
                "<span class=\"preheader\">This is preheader text. Some clients will show this text as a preview.</span>" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"main\">" +
                "<tr>" +
                "<td class=\"wrapper\">" +
                "<p>Hi "+booking.getCreatedByStaff().getFirstName()+",</p>" +
                "<p>We regret to inform you that your lab booking for Course "+booking.getCourse().getCode()+" - " +booking.getCourse().getName()+ ", scheduled from "+booking.getStartTime()+" to "+booking.getEndTime()+", has been rejected by the admin."+
                "<p>Reason : "+rejectReason+".</p>"+
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">" +
                "<tbody>" +
                "<tr>" +
                "<td align=\"left\">" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td> <a href=\"#\" target=\"_blank\">REJECTED</a> </td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "<p>Thank you for your patience and cooperation.</p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "<div class=\"footer\">" +
                "<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tr>" +
                "<td class=\"content-block\">" +
                "<span class=\"apple-link\">University of Jaffna | Faculty of Engineering</span>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</td>" +
                "<td>&nbsp;</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
    }


}
