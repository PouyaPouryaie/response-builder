package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.ExceptionType;

public abstract class SingleValidator implements BusinessValidator {
    protected Object value;
    protected String farsiName;
    protected ExceptionType exceptionType;

    public SingleValidator(Object value, String farsiName, ExceptionType exceptionType) {
        this.value = value;
        this.farsiName = farsiName;
        this.exceptionType = exceptionType;
    }
}
