package com.ecommerce.myshop.service.OrderService;

import com.ecommerce.myshop.dao.checkout.OrderRepository;
import com.ecommerce.myshop.dataTranferObject.OrderDto.OrderDto;
import com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos.OrderCustomerDto;
import com.ecommerce.myshop.dataTranferObject.OrderDto.fieldsDtos.OrderItemDto;
import com.ecommerce.myshop.entity.Checkout.Order;
import com.ecommerce.myshop.entity.Checkout.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orderRepository.findAll()) {
            OrderDto orderDto = new OrderDto();
            OrderCustomerDto orderCustomerDto = new OrderCustomerDto();

            orderDto.setId( order.getId() );
            orderDto.setOrderTrackingNumber( order.getOrderTrackingNumber() );
            orderDto.setTotalPrice( order.getTotalPrice() );
            orderDto.setTotalQuantity( order.getTotalQuantity() );
            orderDto.setDateCreated( order.getDateCreated() );
            orderDto.setLastUpdated( order.getLastUpdated() );

            orderCustomerDto.setFirstName( order.getCustomer().getFirstName() );
            orderCustomerDto.setLastName( order.getCustomer().getLastName() );
            orderCustomerDto.setEmail( order.getCustomer().getEmail() );
            orderCustomerDto.setCity( order.getOrderAddress().getCity() );
            orderCustomerDto.setPhoneNumber( order.getCustomer().getPhoneNumber() );
            orderCustomerDto.setCountry( order.getOrderAddress().getCountry() );
            orderCustomerDto.setZipCode( order.getOrderAddress().getZipCode() );
            orderCustomerDto.setState( order.getOrderAddress().getState() );
            orderCustomerDto.setStreet( order.getOrderAddress().getStreet() );

            orderDto.setCustomer( orderCustomerDto );

            List<OrderItemDto> orderItemDtos = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDto orderItemDto = new OrderItemDto();
                orderItemDto.setId( orderItem.getId() );
                orderItemDto.setProductName( orderItem.getProduct().getProductName() );
                orderItemDto.setQuantity( orderItem.getQuantity() );
                orderItemDto.setUnitPrice( orderItem.getUnitPrice() );
                orderItemDtos.add( orderItemDto );
            }

            orderDto.setOrderItems( orderItemDtos.toArray( new OrderItemDto[0] ) );
            orderDtos.add( orderDto );
        }

        return orderDtos;
    }

    @Override
    public List<OrderDto> getOrdersForUser(String email) {
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orderRepository.findByCustomer_Email(email)) {
            OrderDto orderDto = new OrderDto();
            OrderCustomerDto orderCustomerDto = new OrderCustomerDto();

            orderDto.setId( order.getId() );
            orderDto.setOrderTrackingNumber( order.getOrderTrackingNumber() );
            orderDto.setTotalPrice( order.getTotalPrice() );
            orderDto.setTotalQuantity( order.getTotalQuantity() );
            orderDto.setDateCreated( order.getDateCreated() );
            orderDto.setLastUpdated( order.getLastUpdated() );

            orderCustomerDto.setFirstName( order.getCustomer().getFirstName() );
            orderCustomerDto.setLastName( order.getCustomer().getLastName() );
            orderCustomerDto.setEmail( order.getCustomer().getEmail() );
            orderCustomerDto.setCity( order.getOrderAddress().getCity() );
            orderCustomerDto.setPhoneNumber( order.getCustomer().getPhoneNumber() );
            orderCustomerDto.setCountry( order.getOrderAddress().getCountry() );
            orderCustomerDto.setZipCode( order.getOrderAddress().getZipCode() );
            orderCustomerDto.setState( order.getOrderAddress().getState() );
            orderCustomerDto.setStreet( order.getOrderAddress().getStreet() );

            orderDto.setCustomer( orderCustomerDto );

            List<OrderItemDto> orderItemDtos = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDto orderItemDto = new OrderItemDto();
                orderItemDto.setId( orderItem.getId() );
                orderItemDto.setProductName( orderItem.getProduct().getProductName() );
                orderItemDto.setQuantity( orderItem.getQuantity() );
                orderItemDto.setUnitPrice( orderItem.getUnitPrice() );
                orderItemDtos.add( orderItemDto );
            }

            orderDto.setOrderItems( orderItemDtos.toArray( new OrderItemDto[0] ) );
            orderDtos.add( orderDto );
        }

        return orderDtos;
    }
}
