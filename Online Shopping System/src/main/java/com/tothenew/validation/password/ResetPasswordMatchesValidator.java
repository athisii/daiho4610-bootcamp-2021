package com.tothenew.validation.password;

import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.objects.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ResetPasswordMatchesValidator implements ConstraintValidator<ResetPasswordMatches, Object> {

    @Override
    public void initialize(final ResetPasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final ResetPasswordDto user = (ResetPasswordDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}