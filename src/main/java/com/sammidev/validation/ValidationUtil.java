package com.sammidev.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Component
@AllArgsConstructor
public class ValidationUtil {
    private Validator validator;

    public void validate(Object any) {
        var result = validator.validate(any);
        if (result.size() != 0) {
            throw new ConstraintViolationException(result);
        }
    }
}
