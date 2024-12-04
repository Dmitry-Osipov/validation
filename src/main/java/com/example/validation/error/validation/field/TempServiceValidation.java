package com.example.validation.error.validation.field;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TempServiceValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TempServiceValidation {
    String message() default "Value is null or false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
