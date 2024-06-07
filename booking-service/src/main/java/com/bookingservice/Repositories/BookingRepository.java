package com.bookingservice.Repositories;

import com.bookingservice.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

//    List<Booking> findAllByOrderByCreatedAtDesc();

    @Query("SELECT b FROM Booking b ORDER BY b.date, b.startTime")
    List<Booking> findAllOrderByDateAndStartTime();

    @Query("SELECT b FROM Booking b WHERE b.date = :date AND " +
            "(b.startTime < :endTime AND b.endTime > :startTime)")
    List<Booking> findOverlappingBookings(@Param("date") LocalDate date,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime);


}


