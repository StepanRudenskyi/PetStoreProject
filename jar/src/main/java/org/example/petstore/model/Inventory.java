package org.example.petstore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "INVENTORY")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer inventoryId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "whole_sale_price")
    private Double wholeSalePrice;

    @Column(name = "inventory_date")
    private Date inventoryDate;

    @Column(name = "best_before")
    private Date bestBefore;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
