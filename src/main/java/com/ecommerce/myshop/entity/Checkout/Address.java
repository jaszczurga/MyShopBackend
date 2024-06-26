package com.ecommerce.myshop.entity.Checkout;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column (name = "address_id")
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

    @OneToOne (mappedBy = "orderAddress")
    @PrimaryKeyJoinColumn
    private Order order;
}
