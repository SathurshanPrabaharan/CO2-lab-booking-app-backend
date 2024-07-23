package com.bookingservice.Enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BOOKING_STATUSTest {

    @Test
    public void testBookingStatusValues() {
        BOOKING_STATUS[] expectedValues = { BOOKING_STATUS.PENDING, BOOKING_STATUS.APPROVED, BOOKING_STATUS.REJECTED };
        assertArrayEquals(expectedValues, BOOKING_STATUS.values());
    }

    @Test
    public void testBookingStatusValueOf() {
        assertEquals(BOOKING_STATUS.PENDING, BOOKING_STATUS.valueOf("PENDING"));
        assertEquals(BOOKING_STATUS.APPROVED, BOOKING_STATUS.valueOf("APPROVED"));
        assertEquals(BOOKING_STATUS.REJECTED, BOOKING_STATUS.valueOf("REJECTED"));
    }
}
