package ir.bigz.responsebuilder.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ValidationErrorResponseModel {
    private final Map<String, String> errors;
    private final String path;
}
