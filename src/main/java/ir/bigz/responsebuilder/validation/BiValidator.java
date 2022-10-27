package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.ExceptionType;

public abstract class BiValidator extends SingleValidator {
    protected Object anotherValue;
    protected String anotherFarsiName;

    public BiValidator(Object value, Object anotherValue, String farsiName, String anotherFarsiName, ExceptionType exceptionType) {
        super(value, farsiName, exceptionType);
        this.anotherValue = anotherValue;
        this.anotherFarsiName = anotherFarsiName;
    }

}
