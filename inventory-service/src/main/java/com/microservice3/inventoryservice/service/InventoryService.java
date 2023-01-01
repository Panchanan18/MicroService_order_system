package com.microservice3.inventoryservice.service;

import com.microservice3.inventoryservice.exception.ResourceNotFoundException;
import com.microservice3.inventoryservice.model.Inventory;
import com.microservice3.inventoryservice.model.InventoryResponse;
import com.microservice3.inventoryservice.repo.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes);
        List<InventoryResponse> inventoryResponseList = inventories.stream().map(inventory ->
                        new InventoryResponse().builder().skuCode(inventory.getSkuCode()).inStock(inventory.getQuantity() > 0).build())
                .collect(Collectors.toList());
        return inventoryResponseList;
    }

    public Inventory createInventory(Inventory inventory) {
       return  inventoryRepository.save(inventory);
    }

    public Inventory deltaUpdate(Inventory inventory) throws ResourceNotFoundException {
        Inventory inventory1 = inventoryRepository.findBySkuCode(inventory.getSkuCode()).
                orElseThrow(() -> new ResourceNotFoundException("Inventory with skuCode " + inventory.getSkuCode() + " is not available"));
        inventory1.setQuantity(inventory1.getQuantity()+inventory.getQuantity());
        return inventoryRepository.save(inventory1);
    }

    public Inventory absoluteUpdate(Inventory inventory) throws ResourceNotFoundException {
        Inventory inventory1 = inventoryRepository.findBySkuCode(inventory.getSkuCode())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with skuCode " + inventory.getSkuCode() + " is not available"));
        inventory1.setQuantity(inventory.getQuantity());
        return inventoryRepository.save(inventory1);
    }
}
