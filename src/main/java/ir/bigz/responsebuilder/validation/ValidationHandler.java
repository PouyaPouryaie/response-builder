package ir.bigz.responsebuilder.validation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ir.bigz.responsebuilder.util.CommonUtils;

import java.util.List;

@Component
public record ValidationHandler(ValidationUtils validationUtils) {

    public ValidationHandler(@Qualifier("ValidationUtilsImpl") ValidationUtils validationUtils) {
        this.validationUtils = validationUtils;
    }

    public boolean apply(ValidationType validation, List<String> allowed, Object value, boolean nullable) {

        if (nullable && CommonUtils.isNull(value)) {
            return true;
        } else if (!nullable && CommonUtils.isNull(value)) {
            return false;
        } else {
            return switch (validation) {
                case EMAIL -> validationUtils.isEmailValid((String) value);
                case GENDER -> validationUtils.isGenderValid((String) value, allowed);
                case NATIONAL_CODE -> validationUtils.isNationalCodeValid((String) value);
                case MOBILE -> validationUtils.isMobileValid((String) value);
                case DATE -> validationUtils.isPersianDateFormatValid((String) value);
                case NUMBER -> validationUtils.isNumberValid((String) value);
                case FIELD_NOT_NULL -> validationUtils.isFieldValid(value);
                case SHEBA_NUMBER -> validationUtils.isShebaNumberValid((String) value);
            };
        }
    }
}
