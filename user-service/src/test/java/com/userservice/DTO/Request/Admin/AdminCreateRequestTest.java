package com.userservice.DTO.Request.Admin;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.UUID;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdminCreateRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAdminCreateRequestValid()
        AdminCreateRequest request = AdminCreateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .displayName("johndoe")
                .userRoleId(UUID.randomUUID())
                .tempPassword("password")
                .createdBy(UUID.randomUUID())
                .build();

        Set<ConstraintViolation<AdminCreateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "No validation errors should be present");
    }

    @Test
    public void testAdminCreateRequestInvalid() {
        AdminCreateRequest request = new AdminCreateRequest();

        Set<ConstraintViolation<AdminCreateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Validation errors should be present");

        // Print out the validation errors
        for (ConstraintViolation<AdminCreateRequest> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    // Additional tests can be added for each individual field validation if needed
}
