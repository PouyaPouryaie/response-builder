package ir.bigz.responsebuilder.validation.annotation;

import ir.bigz.responsebuilder.validation.ValidationRequestModelValidator;
import ir.bigz.responsebuilder.validation.ValidationType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ValidationRequestModelValidator.class)
public @interface RequestModelValidator {

    String message() default "not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowed() default {};
    boolean nullable() default false;
    ValidationType value();
}
