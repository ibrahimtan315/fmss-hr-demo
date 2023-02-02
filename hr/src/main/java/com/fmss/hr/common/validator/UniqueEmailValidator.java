package com.fmss.hr.common.validator;

import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return userService.existsUserByEmail(email);
    }
}
