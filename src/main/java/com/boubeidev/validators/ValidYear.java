package com.boubeidev.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy= YearValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {

  String message() default "{javax.validation.constraint.ValidYear.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 2020;




}
