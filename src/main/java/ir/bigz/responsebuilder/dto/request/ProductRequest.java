package ir.bigz.responsebuilder.dto.request;

import ir.bigz.responsebuilder.validation.ValidationType;
import ir.bigz.responsebuilder.validation.annotation.RequestModelValidator;
import lombok.Value;


@Value
public class ProductRequest {

    @RequestModelValidator(ValidationType.FIELD_NOT_NULL)
    private String productName;
    private String description;
    private double price;
}
