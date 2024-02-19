package com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto{
    private Long id;
    private String productName;
    private int quantity;
    private String imageUrl;
    private BigDecimal unitPrice;
    private String dateDelivered;
}
