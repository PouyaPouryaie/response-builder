package ir.bigz.responsebuilder.exception;

import ir.bigz.responsebuilder.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class BigZExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${default-locale:fa}")
    private String DEFAULT_LOCALE;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    private final ResponseBuilder responseBuilder;

    public BigZExceptionHandler(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    private ErrorModel getErrorModel(
            ExceptionType exceptionType, Object[] messageParams, Locale locale) {
        try {
            return ErrorModel.builder()
                    .errorCode(exceptionType.getErrorCode())
                    .message(messageSource.getMessage(
                            exceptionType.getMessageKey(), messageParams, locale))
                    .time(BaseDateUtils.getDateTimeFull())
                    .build();
        } catch (NoSuchMessageException ex) {
            return ErrorModel.builder()
                    .errorCode(exceptionType.getErrorCode())
                    .message(ex.getMessage())
                    .descriptionEN(ex.getMessage())
                    .build();
        }
    }


    private Locale fetchLocale() {
        return BigZUtils.fetchLocale(localeResolver, DEFAULT_LOCALE);
    }

    public ResponseEntity<ApiResponse> createResponse(ExceptionType exceptionType, Object[] messageParams) {
        return createResponse(exceptionType, fetchLocale(), messageParams);
    }

    public ResponseEntity<ApiResponse> createResponse(ExceptionType exceptionType, Locale locale, Object... messageParams) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
        return responseBuilder.buildResponse(httpHeaders, exceptionType.getHttpStatus().value(),
                "خطا در درخواست",
                ApiErrorResponse.builder()
                        .errorModel(getErrorModel(exceptionType, messageParams, locale))
                        .build());
    }

    @ExceptionHandler({BigZException.class})
    public ResponseEntity<ApiResponse> handleRhaHandledException(
            final BigZException ex, final WebRequest webRequest) {
        return createResponse(ex, fetchLocale(), ex.getMessageParams());
    }

    @ExceptionHandler({BeanCreationException.class})
    public ResponseEntity<ApiResponse> handleBeanCreationException(
            final BeanCreationException ex, final WebRequest webRequest) {
        if (ex.getCause() instanceof BeanInstantiationException) {
            if (ex.getCause().getCause() instanceof BigZException) {
                return handleRhaHandledException((BigZException) ex.getCause().getCause(), webRequest);
            }
        }
        return createResponse(CommonExceptionType.INTERNAL_SERVER_ERROR, fetchLocale(), new Object[0]);
    }

    @ExceptionHandler({Exception.class, Throwable.class, RuntimeException.class})
    public ResponseEntity<ApiResponse> handleUnhandledException(
            final Throwable ex, final WebRequest webRequest) {
        return createResponse(CommonExceptionType.INTERNAL_SERVER_ERROR, fetchLocale());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorResponse apiResponseErrorBuilder = ApiErrorResponse.builder()
                .validationErrorModel(
                        ValidationErrorModel
                                .builder()
                                .errorCode(CommonExceptionType.DATA_INPUT_INVALID.getErrorCode())
                                .time(BaseDateUtils.getDateTimeFull())
                                .validationError(ValidationErrorResponseModel
                                        .builder()
                                        .errors(errors)
                                        .path(request.getDescription(false))
                                        .build())
                                .build()).build();

        ResponseEntity<ApiResponse> apiResponseResponseEntity = responseBuilder.buildResponse(CommonExceptionType.DATA_INPUT_INVALID.getHttpStatus().value(),
                messageSource.getMessage(CommonExceptionType.DATA_INPUT_INVALID.getMessageKey(), null, fetchLocale()), apiResponseErrorBuilder);
        ApiResponse body = apiResponseResponseEntity.getBody();

        return ResponseEntity
                .status(apiResponseResponseEntity.getStatusCode())
                .headers(httpHeaders -> httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8"))
                .body(body);
    }
}
