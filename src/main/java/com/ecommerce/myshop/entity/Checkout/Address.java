package com.ecommerce.myshop.entity.Checkout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "street")
    private String street;

    @Column (name = "city")
    private String city;

    @Column (name = "state")
    private String state;

    @Column (name = "country")
    private String country;

    @Column (name = "zip_code")
    private String zipCode;

    @OneToOne (mappedBy = "shippingAddress")
    @PrimaryKeyJoinColumn
    private Order order;
}