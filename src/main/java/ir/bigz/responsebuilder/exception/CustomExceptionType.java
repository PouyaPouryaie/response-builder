package ir.bigz.responsebuilder.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class CustomExceptionType implements ExceptionType{
    HttpStatus httpStatus;
    int errorCode;
    String messageKey;
}
