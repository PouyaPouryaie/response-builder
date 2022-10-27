package ir.bigz.responsebuilder.exception;

import org.springframework.http.HttpStatus;

public class BigZException extends RuntimeException implements ExceptionType {

    private ExceptionType exceptionType;
    private Object[] messageParams;

    public BigZException(HttpStatus httpStatus, String messageKey, int errorCode, Throwable cause) {
        this(httpStatus, messageKey, errorCode, null, cause);
    }

    public BigZException(HttpStatus httpStatus, String messageKey, int errorCode, Object[] messageParams, Throwable cause) {
        this(new CustomExceptionType(httpStatus, errorCode, messageKey), messageParams, cause);
    }

    public BigZException(ExceptionType exceptionType) {
        this(exceptionType, null, null);
    }

    public BigZException(ExceptionType exceptionType, Object[] messageParams) {
        this(exceptionType, messageParams, null);
    }

    public BigZException(ExceptionType exceptionType, Object[] messageParams, Throwable cause) {
        super(cause);
        this.exceptionType = exceptionType;
        this.messageParams = messageParams;
    }

    public String getMessageKey() {
        return exceptionType.getMessageKey();
    }

    public int getErrorCode() {
        return exceptionType.getErrorCode();
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getHttpStatus();
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public Object[] getMessageParams() {
        return messageParams;
    }
}
