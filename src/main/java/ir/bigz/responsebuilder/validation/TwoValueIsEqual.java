package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.BigZException;
import ir.bigz.responsebuilder.exception.CommonExceptionType;
import ir.bigz.responsebuilder.exception.ExceptionType;
import ir.bigz.responsebuilder.util.NumberUtils;

public class TwoValueIsEqual extends BiValidator{

    public TwoValueIsEqual(Object value, Object anotherValue, String farsiName, String anotherFarsiName, ExceptionType exceptionType) {
        super(value, anotherValue, farsiName, anotherFarsiName, exceptionType);
    }

    @Override
    public void validateAndThrowException() {

        if(anotherValue != null){
            switch (value){
                case String s -> callException(!s.equals(anotherValue));
                case Long n -> callException(n.longValue() != NumberUtils.longValue(anotherValue));
                case Integer n -> callException(n.intValue() != NumberUtils.integerValue(anotherValue));
                case Double n -> callException(n.doubleValue() != NumberUtils.doubleValue(anotherValue));
                case null -> callException(true);
                default -> throw new BigZException(CommonExceptionType.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            callException(true);
        }
    }

    private void callException(boolean raiseException){
        if(raiseException){
            throw new BigZException(exceptionType, new String[0]);
        }
    }
}
