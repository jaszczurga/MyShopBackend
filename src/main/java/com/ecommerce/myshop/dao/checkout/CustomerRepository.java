package com.ecommerce.myshop.dao.checkout;

import com.ecommerce.myshop.entity.Checkout.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findByEmail(String theEmail);
}
