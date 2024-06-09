package com.bookingservice.Validations.Validators;

import com.bookingservice.Enums.COURSE_TYPE;
import com.bookingservice.Validations.ValidCourseType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseTypeValidator implements ConstraintValidator<ValidCourseType, String> {

    @Override
    public void initialize(ValidCourseType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            COURSE_TYPE.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
