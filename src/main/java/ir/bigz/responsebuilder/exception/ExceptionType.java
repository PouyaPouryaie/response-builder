package ir.bigz.responsebuilder.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

    HttpStatus getHttpStatus();
    int getErrorCode();
    String getMessageKey();
}
