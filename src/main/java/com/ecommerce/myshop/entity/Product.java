package com.ecommerce.myshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Data // Lombok annotation to generate getters, setters, constructors, toString, hash, equals, etc.
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "price")
    private double productPrice;

    @Column(name = "stock_quantity")
    private int productStockQuantity;

    @Column(name = "product_img")
    private String productImage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private ProductCategory category;
}
