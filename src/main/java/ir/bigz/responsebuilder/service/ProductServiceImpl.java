package ir.bigz.responsebuilder.service;

import ir.bigz.responsebuilder.dao.ProductRepository;
import ir.bigz.responsebuilder.dto.request.ProductRequest;
import ir.bigz.responsebuilder.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ir.bigz.responsebuilder.util.ApiResponse;
import ir.bigz.responsebuilder.util.ResponseBuilder;

import java.util.List;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ResponseBuilder responseBuilder;

    public ProductServiceImpl(ProductRepository productRepository,
                              ResponseBuilder responseBuilder) {
        this.productRepository = productRepository;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<Product> findAllProduct = productRepository.findAll();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "all product", findAllProduct);
    }

    @Override
    public ResponseEntity<ApiResponse> saveProduct(ProductRequest productRequest) {
        Product newProduct = Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(newProduct);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "save product");
    }
}
