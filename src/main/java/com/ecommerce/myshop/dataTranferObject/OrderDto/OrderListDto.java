package com.ecommerce.myshop.dataTranferObject.OrderDto;

import com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos.OrderCustomerDto;
import com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos.OrderOrderItemDto;

public class OrderListDto{

    private Long id;
    private String orderTrackingNumber;
    private int totalQuantity;
    private String totalPrice;
    private String status;
    private String dateCreated;
    private String lastUpdated;

    //it consists customer details such as address also
    private OrderCustomerDto customer;
    private OrderOrderItemDto[] orderItems;

}
