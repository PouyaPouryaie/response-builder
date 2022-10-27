package ir.bigz.responsebuilder.service;

import ir.bigz.responsebuilder.dto.request.ProductRequest;
import org.springframework.http.ResponseEntity;
import ir.bigz.responsebuilder.util.ApiResponse;

public interface ProductService {

    ResponseEntity<ApiResponse> getAll();
    ResponseEntity<ApiResponse> saveProduct(ProductRequest productRequest);
}
