package ir.bigz.responsebuilder.controller;

import ir.bigz.responsebuilder.dto.request.ProductRequest;
import ir.bigz.responsebuilder.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ir.bigz.responsebuilder.util.ApiResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier("productServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProduct(){
        return productService.getAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@Valid  @RequestBody ProductRequest productRequest){
        return productService.saveProduct(productRequest);
    }
}
