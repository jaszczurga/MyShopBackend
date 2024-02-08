package com.ecommerce.myshop.dataTranferObject.checkout;

import com.ecommerce.myshop.entity.Checkout.Address;
import com.ecommerce.myshop.entity.Checkout.Customer;
import com.ecommerce.myshop.entity.Checkout.Order;
import com.ecommerce.myshop.entity.Checkout.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class PurchaseDto {
    private Customer customer;
    private Address address;
    private Order order;
    private Set<OrderItem> orderItems;

}
