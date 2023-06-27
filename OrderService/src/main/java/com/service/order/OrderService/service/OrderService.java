package com.service.order.OrderService.service;

import com.service.order.OrderService.dto.OrderItemsDto;
import com.service.order.OrderService.dto.OrderRequest;
import com.service.order.OrderService.external.InventoryService;
import com.service.order.OrderService.model.Order;
import com.service.order.OrderService.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    InventoryService inventoryService;
    OrderService(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        List<String> skuCodes = orderRequest.getOrderItemsDto()
                .stream()
                .map(orderItemsDto -> {
                    return orderItemsDto.getSkuCode();}
                ).toList();
        inventoryService.isInStock();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems = orderRequest.getOrderItemsDto()
                .stream()
                .map(orderItemsDto ->mapToDto(orderItemsDto)).toList();
        order.setOrderItemsList(orderItems);
        return "Success";
    }
    private OrderItems mapToDto(OrderItemsDto orderItemsDto){
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItems.getQuantity());
        orderItems.setSkuCode(orderItems.getSkuCode());
        return orderItems;
    }
}
