package org.example.petstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cart")
@ToString(exclude = "product")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "addition_date", nullable = false)
    private LocalDateTime additionTime;

    public Cart() {
        this.additionTime = LocalDateTime.now();
    }
    public Cart(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.additionTime = LocalDateTime.now();
    }

    public BigDecimal calculateTotalPrice() {
        return product.getRetailPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
