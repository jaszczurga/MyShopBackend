package com.ecommerce.myshop.entity.Checkout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column (name = "order_id")
    private Long id;

    @Column (name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column (name = "total_quantity")
    private int totalQuantity;

    @Column (name = "total_price")
    private BigDecimal totalPrice;

    @Column (name = "status")
    private String status;

    @Column (name = "date_create")
    @CreationTimestamp
    private String dateCreated;

    @Column (name = "date_update")
    @UpdateTimestamp
    private String lastUpdated;


    @OneToMany (cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "order_address", referencedColumnName = "address_id")
    private Address orderAddress;


    public void add(OrderItem item) {
        if (item != null) {
            if (orderItems == null) {
                orderItems = new HashSet<>();
            }
            orderItems.add(item);
            item.setOrder(this);
        }
    }


}
