package com.configurationservice.DTO.Request.Course;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseCreateRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCourseCreateRequest() {
        CourseCreateRequest request = CourseCreateRequest.builder()
                .code("CSE101")
                .name("Computer Science Basics")
                .courseType("TECHNICAL")
                .departmentId(UUID.randomUUID())
                .semester((short) 1)
                .coordinatorId(UUID.randomUUID())
                .responsibleStaffIds(Set.of(UUID.randomUUID()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(UUID.randomUUID())
                .build();

        Set<ConstraintViolation<CourseCreateRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }
    @Test
    public void testInvalidCourseCreateRequest() {
        CourseCreateRequest request = CourseCreateRequest.builder()
                .code("")
                .name("")
                .courseType(null)
                .createdBy(null)
                .build();

        Set<ConstraintViolation<CourseCreateRequest>> violations = validator.validate(request);
        assertEquals(5, violations.size());

        for (ConstraintViolation<CourseCreateRequest> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
