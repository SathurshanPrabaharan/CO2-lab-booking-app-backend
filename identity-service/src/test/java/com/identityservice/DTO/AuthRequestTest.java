package com.identityservice.DTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAuthRequest() {
        AuthRequest authRequest = AuthRequest.builder()
                .userName("testUser")
                .password("securePassword")
                .build();

        Set<ConstraintViolation<AuthRequest>> violations = validator.validate(authRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidAuthRequest() {
        AuthRequest authRequest = AuthRequest.builder()
                .userName("")
                .password("short")
                .build();

        Set<ConstraintViolation<AuthRequest>> violations = validator.validate(authRequest);
        assertEquals(2, violations.size());

        for (ConstraintViolation<AuthRequest> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
