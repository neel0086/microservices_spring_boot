package com.service.order.OrderService.service;

import com.service.order.OrderService.dto.OrderItemsDto;
import com.service.order.OrderService.dto.OrderRequest;
import com.service.order.OrderService.event.OrderPlacedEvent;
import com.service.order.OrderService.external.dto.InventoryResponse;
import com.service.order.OrderService.external.service.InventoryService;
import com.service.order.OrderService.model.Order;
import com.service.order.OrderService.model.OrderItems;
import com.service.order.OrderService.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    InventoryService inventoryService;

    OrderRepository orderRepository;

    private final KafkaTemplate kafkaTemplate;
    OrderService(OrderRepository orderRepository, InventoryService inventoryService, KafkaTemplate kafkaTemplate){
        this.inventoryService=inventoryService;
        this.orderRepository=orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        List<String> skuCodes = orderRequest.getOrderItemsDto()
                .stream()
                .map(orderItemsDto -> {
                    return orderItemsDto.getSkuCode();}
                ).toList();
        List<InventoryResponse> inventoryResponses = inventoryService.isInStock(skuCodes).getBody();
        System.out.println(inventoryResponses);
        boolean flag=true;
        for(InventoryResponse ir : inventoryResponses){
            if(!ir.isInStock()){
                flag=false;
            }
        }
        if(flag) {
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderItems> orderItems = orderRequest.getOrderItemsDto()
                    .stream()
                    .map(orderItemsDto -> mapToDto(orderItemsDto)).toList();
            order.setOrderItemsList(orderItems);
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
        }
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
