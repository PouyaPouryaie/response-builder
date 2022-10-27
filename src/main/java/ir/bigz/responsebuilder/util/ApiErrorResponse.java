package ir.bigz.responsebuilder.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.bigz.responsebuilder.exception.ErrorModel;
import ir.bigz.responsebuilder.exception.ValidationErrorModel;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiErrorResponse {

    @JsonProperty("validation")
    private ValidationErrorModel validationErrorModel;
    @JsonProperty("error")
    private ErrorModel errorModel;
}
