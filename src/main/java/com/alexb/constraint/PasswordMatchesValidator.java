package com.alexb.constraint;

import com.alexb.constraint.annotations.PasswordMatches;
import com.alexb.model.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto userRegistrationDto, ConstraintValidatorContext constraintValidatorContext) {
        final String password = userRegistrationDto.getPassword();
        final String confirmationPassword = userRegistrationDto.getConfirmationPassword();

        return password.equals(confirmationPassword);
    }
}