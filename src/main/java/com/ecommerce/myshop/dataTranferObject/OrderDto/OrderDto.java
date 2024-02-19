package com.ecommerce.myshop.dataTranferObject.OrderDto;

import com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos.OrderCustomerDto;
import com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto{

    private Long id;
    private String orderTrackingNumber;
    private int totalQuantity;
    private BigDecimal totalPrice;
    private String status;
    private String dateCreated;
    private String lastUpdated;

    //it consists customer details such as address also
    private OrderCustomerDto customer;
    private OrderItemDto[] orderItems;

}
