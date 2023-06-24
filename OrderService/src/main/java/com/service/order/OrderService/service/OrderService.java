package com.service.order.OrderService.service;

import com.service.order.OrderService.dto.OrderItemsDto;
import com.service.order.OrderService.dto.OrderRequest;
import com.service.order.OrderService.model.Order;
import com.service.order.OrderService.model.OrderItems;

import java.util.List;
import java.util.UUID;

public class OrderService {
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems = orderRequest.getOrderItemsDto()
                .stream()
                .map(orderItemsDto ->mapToDto(orderItemsDto)).toList();
        order.setOrderItemsList(orderItems);
    }
    private OrderItems mapToDto(OrderItemsDto orderItemsDto){
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItems.getQuantity());
        orderItems.setSkuCode(orderItems.getSkuCode());
        return orderItems;
    }
}
