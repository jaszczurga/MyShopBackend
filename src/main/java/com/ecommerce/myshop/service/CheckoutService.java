package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.checkout.PaymentInfoDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
    PurchaseResponseDto placeOrder(PurchaseDto purchase);

    PaymentIntent createPaymentIntent(PaymentInfoDto paymentInfoDto) throws StripeException;

}
