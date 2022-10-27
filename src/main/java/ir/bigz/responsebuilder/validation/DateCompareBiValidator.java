package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.BigZException;
import ir.bigz.responsebuilder.exception.CommonExceptionType;
import ir.bigz.responsebuilder.util.CommonUtils;

public class DateCompareBiValidator extends BiValidator {

    public DateCompareBiValidator(String value1, String value2, String farsiName1, String farsiName2) {
        super(value1, value2, farsiName1, farsiName2, null);
    }

    @Override
    public void validateAndThrowException() {
        if(CommonUtils.isNotNull(value) && CommonUtils.isNotNull(anotherValue)){
            String firstValue = (String) value;
            String secondValue = (String) anotherValue;
            if(firstValue.compareTo(secondValue) > 0){
                throw new BigZException(CommonExceptionType.COMPARE_DATE_INVALID, new String[]{farsiName, anotherFarsiName});
            }
        }
    }
}
