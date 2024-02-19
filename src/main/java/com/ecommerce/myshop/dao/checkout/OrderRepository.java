package com.ecommerce.myshop.dao.checkout;

import com.ecommerce.myshop.entity.Checkout.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Order[] findByCustomer_Email(String email);
}
