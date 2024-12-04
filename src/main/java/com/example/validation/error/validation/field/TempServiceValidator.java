package com.example.validation.error.validation.field;

import com.example.validation.domain.service.TempService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TempServiceValidator implements ConstraintValidator<TempServiceValidation, Boolean> {
    private final TempService tempService;

    @Override
    public boolean isValid(Boolean aBoolean, ConstraintValidatorContext constraintValidatorContext) {
        if (aBoolean == null) {
            return false;
        }

        return tempService.exists(aBoolean);
    }
}
