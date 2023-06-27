package com.service.inventory.service;

import com.service.inventory.dto.InventoryResponse;
import com.service.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        log.info("Checking Inventory");
        List<InventoryResponse> inventoryResponses = new ArrayList<>();

        for (String skuCode : skuCodes) {
            boolean isInStock = inventoryRepository.existsBySkuCode(skuCode);
            InventoryResponse inventoryResponse = new InventoryResponse(skuCode, isInStock);
            inventoryResponses.add(inventoryResponse);
        }
        return inventoryResponses;
    }
}