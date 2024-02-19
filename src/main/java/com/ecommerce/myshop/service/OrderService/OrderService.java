package com.ecommerce.myshop.service.OrderService;

import com.ecommerce.myshop.dataTranferObject.OrderDto.OrderDto;

import java.util.List;

public interface OrderService {

    //get all orders method
    public List<OrderDto> getAllOrders();

    List<OrderDto> getOrdersForUser(String email);

    //get order for a user method


}
