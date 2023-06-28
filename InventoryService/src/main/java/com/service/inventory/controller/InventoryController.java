package com.service.inventory.controller;

import com.service.inventory.dto.InventoryResponse;
import com.service.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestBody List<String> skuCodes) {
//        log.info("Received inventory check request for skuCode: {}", skuCodes);
        System.out.println("Request succesfull, Feign client working");
        return ResponseEntity.ok(inventoryService.isInStock(skuCodes));
    }
}