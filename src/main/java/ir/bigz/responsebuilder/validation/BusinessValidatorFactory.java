package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.ExceptionType;

import java.util.Map;

public class BusinessValidatorFactory {

    public static BiValidator createDateCompareBiValidator(String value1, String value2, String farsiName1, String farsiName2) {
        return new DateCompareBiValidator(value1, value2, farsiName1, farsiName2);
    }

    public static BiValidator createTwoValueIsEqual(Object value1, Object value2, String farsiName1, String farsiName2, ExceptionType exceptionType) {
        return new TwoValueIsEqual(value1, value2, farsiName1, farsiName2, exceptionType);
    }

    public static SingleValidator createDataCheckInMap(Object value1, Map map, String farsiName1, ExceptionType exceptionType) {
        return new DataCheckInMap(value1, map, farsiName1, exceptionType);
    }

    public static NullValidator createNullValidator(Object value1, String farsiName1, ExceptionType exceptionType) {
        return new NullValidator(value1, farsiName1, exceptionType);
    }
}
