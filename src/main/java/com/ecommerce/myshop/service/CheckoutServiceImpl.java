package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dao.checkout.CustomerRepository;
import com.ecommerce.myshop.dao.checkout.OrderRepository;
import com.ecommerce.myshop.dataTranferObject.checkout.PaymentInfoDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseResponseDto;
import com.ecommerce.myshop.entity.Checkout.Customer;
import com.ecommerce.myshop.entity.Checkout.Order;
import com.ecommerce.myshop.entity.Checkout.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;


    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               OrderRepository orderRepository,
                               @Value ("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    public PurchaseResponseDto placeOrder(PurchaseDto purchase) {
        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        order.setOrderAddress( purchase.getAddress() );

        Customer customer = purchase.getCustomer();

        String theEmail = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(theEmail);
        if(customerFromDB != null) {
            customer = customerFromDB;
        }
        customer.add(order);
        customerRepository.save(customer);

        return new PurchaseResponseDto(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfoDto paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String,Object> params = new HashMap<>();
        params.put("amount",paymentInfo.getAmount());
        params.put("currency",paymentInfo.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);
        params.put("description","zakupkiii");
        params.put("receipt_email",paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params); // this is a Stripe API call to create a PaymentIntent object
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
