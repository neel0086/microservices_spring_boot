package com.service.order.OrderService.external.service;

import com.service.order.OrderService.external.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="INVENTORY-SERVICE")
public interface InventoryService {
    @PostMapping("/inventory")
    public ResponseEntity<List<InventoryResponse>> isInStock(List<String> skuCodes);
}
