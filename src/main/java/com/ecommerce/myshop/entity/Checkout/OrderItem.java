package com.ecommerce.myshop.entity.Checkout;

import com.ecommerce.myshop.entity.Product;
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
    @Column (name = "order_item_id")
    private Long id;

    @Column (name = "price")
    private BigDecimal unitPrice;

    @Column (name = "quantity")
    private int quantity;

    @OneToOne
    @JoinColumn (name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn (name = "order_id")
    private Order order;

}
