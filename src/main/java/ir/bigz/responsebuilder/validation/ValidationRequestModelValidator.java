package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.validation.annotation.RequestModelValidator;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ValidationRequestModelValidator implements ConstraintValidator<RequestModelValidator, String> {

    boolean nullable;
    List<String> allowed;
    ValidationType validationsType;

    private final ValidationHandler validationHandler;

    public ValidationRequestModelValidator(ValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }

    @Override
    public void initialize(RequestModelValidator constraintAnnotation) {
        validationsType = constraintAnnotation.value();
        nullable = constraintAnnotation.nullable();
        if(constraintAnnotation.allowed().length > 0){
            allowed = Arrays.asList(constraintAnnotation.allowed());
        }
        else if(validationsType.getDefaultValue().length > 0){
            allowed = Arrays.asList(validationsType.getDefaultValue());
        }
        else {
            allowed = Collections.emptyList();
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if(!validationHandler.apply(validationsType, allowed, value, nullable)){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(validationsType.getFailedMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }


}
