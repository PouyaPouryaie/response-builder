package ir.bigz.responsebuilder.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@JsonPropertyOrder({"httpHeaders", "httpStatusCode", "message", "data", "error", "otherParams"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {

    @JsonIgnore
    private final HttpHeaders httpHeaders;
    @JsonIgnore
    private final int httpStatusCode;
    private final String message;
    private final T data;
    @JsonProperty(value = "error")
    private final ApiErrorResponse apiErrorResponse;
    private final Map<String, Object> otherParams;


    public ApiResponse(ApiResponseBuilder builder) {
        this.httpHeaders = builder.httpHeaders;
        this.httpStatusCode = builder.httpStatusCode;
        this.message = builder.message;
        this.data = (T) builder.data;
        this.apiErrorResponse = builder.apiErrorResponse;
        this.otherParams = builder.otherParams;
    }

    public static class ApiResponseBuilder<T> {

        private HttpHeaders httpHeaders;
        private final int httpStatusCode;
        private final String message;
        private T data;
        private Map<String, Object> otherParams;
        private ApiErrorResponse apiErrorResponse;

        public ApiResponseBuilder(int httpStatusCode, String message) {
            this.httpStatusCode = httpStatusCode;
            this.message = message;
        }

        public ApiResponseBuilder<T> withHttpHeader(HttpHeaders httpHeader) {
            this.httpHeaders = httpHeader;
            return this;
        }

        public ApiResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> withError(ApiErrorResponse apiResponseError){
            this.apiErrorResponse = apiResponseError;
            return this;
        }

        public ApiResponseBuilder<T> withOtherParams(Map<String, Object> otherParams) {
            this.otherParams = otherParams;
            return this;
        }

        public ResponseEntity<ApiResponse> build(){
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return ResponseEntity.status(apiResponse.getHttpStatusCode()).headers(apiResponse.getHttpHeaders())
                    .body(apiResponse);
        }
    }

}
