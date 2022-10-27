package ir.bigz.responsebuilder.validation;

import ir.bigz.responsebuilder.exception.BigZException;
import ir.bigz.responsebuilder.exception.ExceptionType;
import ir.bigz.responsebuilder.util.CommonUtils;

import java.util.Map;

public class DataCheckInMap extends SingleValidator{

    protected Map maps;

    public DataCheckInMap(Object value, Map maps, String farsiName, ExceptionType exceptionType) {
        super(value, farsiName, exceptionType);
        this.maps = maps;
    }

    @Override
    public void validateAndThrowException() {
        if(CommonUtils.isNull(maps.get(value))){
            throw new BigZException(exceptionType, new String[0]);
        }
    }
}
