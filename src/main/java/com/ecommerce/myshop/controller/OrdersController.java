package com.ecommerce.myshop.controller;


import com.ecommerce.myshop.dataTranferObject.OrderDto.OrderDto;
import com.ecommerce.myshop.dataTranferObject.OrderDto.OrderResponse;
import com.ecommerce.myshop.service.Authentication.AuthenticationService;
import com.ecommerce.myshop.service.OrderService.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/orders")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class OrdersController{

    final OrderService orderService;
    final AuthenticationService authenticationService;


   @GetMapping ("/allOrders")
public ResponseEntity<OrderResponse> getAllOrders() {
    List<OrderDto> orderDtoList = orderService.getAllOrders();
    OrderResponse orderResponse = new OrderResponse(orderDtoList);
    return ResponseEntity.ok(orderResponse);
}

//get order for a user by email method
    @GetMapping ("/customerOrders")
    public ResponseEntity<OrderResponse> getOrdersForUser() {

       //get email from the authentication service
String email = authenticationService.getAuthenticatedUserEmail();



        List<OrderDto> orderDtoList = orderService.getOrdersForUser(email);
        OrderResponse orderResponse = new OrderResponse(orderDtoList);
        return ResponseEntity.ok(orderResponse);
    }


}
