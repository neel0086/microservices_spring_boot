package com.service.order.OrderService.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="INVENTORY-SERVICE")
public interface    InventoryService {
    @PostMapping("/inventory")
    public Boolean isInStock();
}
