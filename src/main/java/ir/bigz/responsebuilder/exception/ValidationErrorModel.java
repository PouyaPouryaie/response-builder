package ir.bigz.responsebuilder.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationErrorModel extends ErrorModel{
    @JsonProperty(value = "validationError", index = 7)
    private final ValidationErrorResponseModel validationError;
}
