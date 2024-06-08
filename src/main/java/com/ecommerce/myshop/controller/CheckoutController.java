package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.Exceptions.RunOutOfStock;
import com.ecommerce.myshop.dataTranferObject.checkout.PaymentInfoDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseResponseDto;
import com.ecommerce.myshop.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("http://localhost:4200")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
//
//    @PostMapping("/purchase")
//    public PurchaseResponseDto placeOrder(@RequestBody PurchaseDto purchase) {
//        PurchaseResponseDto purchaseResponse = checkoutService.placeOrder(purchase);
//        return purchaseResponse;
//    }

    @PostMapping("/purchase")
public ResponseEntity<?> placeOrder(@RequestBody PurchaseDto purchase) {
    final int MAX_RETRIES = 50;
    for (int i = 0; i < MAX_RETRIES; i++) {
        try {
            PurchaseResponseDto purchaseResponse = checkoutService.placeOrder(purchase);
            return new ResponseEntity<>(purchaseResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
    return new ResponseEntity<>("Order couldnt be processed ", HttpStatus.INTERNAL_SERVER_ERROR);
}

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoDto paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }



}
