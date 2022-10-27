package ir.bigz.responsebuilder.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long productId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false)
    private double price;
}
