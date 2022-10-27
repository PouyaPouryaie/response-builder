package ir.bigz.responsebuilder.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Objects;

public enum CommonExceptionType implements ExceptionType {

    UNKNOWN                                  (HttpStatus.INTERNAL_SERVER_ERROR, 99999, "common.bigZ.bad-request"),
    INTERNAL_SERVER_ERROR                    (HttpStatus.INTERNAL_SERVER_ERROR, 20001, "common.bigZ.internal-server-error"),
    PERMISSION_DENIED                        (HttpStatus.FORBIDDEN, 20002, "common.bigZ.permission-denied"),
    DATA_INPUT_INVALID                       (HttpStatus.BAD_REQUEST, 20003, "bigZ.validation.generalError"),
    COMPARE_DATE_INVALID                     (HttpStatus.BAD_REQUEST, 20004, "bigZ.validation.compareDate"),
    BAD_REQUEST                              (HttpStatus.BAD_REQUEST, 20005, "common.bigZ.bad-request"),
    NOT_FOUND                                (HttpStatus.NOT_FOUND, 20006, "bigZ.product.not-found"),
    ;

    private CustomExceptionType customExceptionType;

    CommonExceptionType(HttpStatus httpStatus, int errorCode, String messageKey) {
        this.customExceptionType = new CustomExceptionType(httpStatus, errorCode, messageKey);
    }

    public static CustomExceptionType of(String messageKey) {
        return Arrays.stream(values())
                .filter(a -> Objects.equals(a.getMessageKey(), messageKey))
                .findFirst()
                .orElse(UNKNOWN)
                .getCustomExceptionType();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.customExceptionType.httpStatus;
    }

    @Override
    public int getErrorCode() {
        return this.customExceptionType.errorCode;
    }

    @Override
    public String getMessageKey() {
        return this.customExceptionType.messageKey;
    }

    public CustomExceptionType getCustomExceptionType() {
        return this.customExceptionType;
    }
}
