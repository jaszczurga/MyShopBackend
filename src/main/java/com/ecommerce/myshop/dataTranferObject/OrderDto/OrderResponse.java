package com.ecommerce.myshop.dataTranferObject.OrderDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private List<OrderDto> orders;
}