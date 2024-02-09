package com.ecommerce.myshop.dataTranferObject.checkout;

import lombok.Data;

@Data
public class PaymentInfoDto {
    private int amount; //Stripe wants amount in cents so we will convert it
    private String currency;
    private String receiptEmail;
}
