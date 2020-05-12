package com.cyc.demo1.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * @author chenyuchuan
 */
public class CustomValidator implements ConstraintValidator<CustomValidation, String> {
    @Override
    public void initialize(CustomValidation customValidation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.hasText(value)) {
            if (value.contains("cyc")) {
                return true;
            }
        }

        return false;
    }
}
