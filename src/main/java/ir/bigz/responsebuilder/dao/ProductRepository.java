package ir.bigz.responsebuilder.dao;

import ir.bigz.responsebuilder.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
