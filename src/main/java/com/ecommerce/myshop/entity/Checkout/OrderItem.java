package com.ecommerce.myshop.entity.Checkout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table (name = "order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "price")
    private BigDecimal unitPrice;

    @Column (name = "quantity")
    private int quantity;

    @Column (name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn (name = "order_id")
    private Order order;

}
