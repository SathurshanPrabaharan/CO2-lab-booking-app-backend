package com.inventoryservice.DTO.Response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResponseMessageTest {

    @Test
    public void testAllArgsConstructor() {
        ResponseMessage responseMessage = new ResponseMessage("Success");
        assertEquals("Success", responseMessage.getMessage());
    }

    @Test
    public void testNoArgsConstructor() {
        ResponseMessage responseMessage = new ResponseMessage();
        assertNull(responseMessage.getMessage());
        responseMessage.setMessage("Error");
        assertEquals("Error", responseMessage.getMessage());
    }

    @Test
    public void testSetMessage() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage("Updated Message");
        assertEquals("Updated Message", responseMessage.getMessage());
    }
}
