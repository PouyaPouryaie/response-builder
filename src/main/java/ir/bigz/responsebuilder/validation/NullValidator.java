package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.BigZException;
import ir.bigz.responsebuilder.exception.ExceptionType;
import ir.bigz.responsebuilder.util.CommonUtils;

public class NullValidator extends SingleValidator{

    public NullValidator(Object value, String farsiName, ExceptionType exceptionType) {
        super(value, farsiName, exceptionType);
    }

    @Override
    public void validateAndThrowException() {
        if(CommonUtils.isNull(value)){
            throw new BigZException(exceptionType);
        }
    }
}
