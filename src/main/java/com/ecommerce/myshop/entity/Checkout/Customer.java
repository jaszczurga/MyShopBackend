package com.ecommerce.myshop.entity.Checkout;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table (name = "customers")
@Getter // Lombok annotation to generate getters
@Setter // Lombok annotation to generate setters
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column (name = "customer_id")
    private Long id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column (name = "email")
    private String email;

    @Column (name = "phone_number")
    private String phoneNumber;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders;

    public void add(Order order) {
        if (order != null) {
            if (orders == null) {
                orders = new HashSet<>();
            }
            orders.add(order);
            order.setCustomer(this);
        }
    }
}
