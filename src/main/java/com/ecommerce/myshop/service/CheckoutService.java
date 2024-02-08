package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseResponseDto;

public interface CheckoutService {
    PurchaseResponseDto placeOrder(PurchaseDto purchase);

}
