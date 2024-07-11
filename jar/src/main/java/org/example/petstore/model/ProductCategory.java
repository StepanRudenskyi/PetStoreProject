package org.example.petstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ProductCategory")
@ToString(exclude = "products")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "storage_conditions")
    private String storageCondition;

    @Column(name = "sale_limitation")
    private String saleLimitation;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
