package com.ecommerce.myshop.service;

import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseDto;
import com.ecommerce.myshop.dataTranferObject.checkout.PurchaseResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    @Override
    public PurchaseResponseDto placeOrder(PurchaseDto purchase) {
        return null;
    }
}
