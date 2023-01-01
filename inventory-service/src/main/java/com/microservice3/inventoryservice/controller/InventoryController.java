package com.microservice3.inventoryservice.controller;

import com.microservice3.inventoryservice.exception.ResourceNotFoundException;
import com.microservice3.inventoryservice.model.Inventory;
import com.microservice3.inventoryservice.model.InventoryResponse;
import com.microservice3.inventoryservice.service.InventoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    // this method will check inventory is available for the given skuCodes
    @GetMapping("/isInStock")
    @ApiOperation(value = "Check If Inventory is in Stock")
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }
    // this method will create inventory
    @PostMapping("/create")
    @ApiOperation(value = "Create Inventory")

    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    // this method will give delta update to the inventory
    @PutMapping("/update/delta")
    @ApiOperation(value = "Delta Update")

    public Inventory deltaUpdate(@RequestBody Inventory inventory) throws ResourceNotFoundException {
        return inventoryService.deltaUpdate(inventory);
    }

    // this method will give absolute update to the inventory
    @PutMapping("/update/absolute")
    @ApiOperation(value = "Absolute Update")
    public Inventory absoluteUpdate(@RequestBody Inventory inventory) throws ResourceNotFoundException {
        return inventoryService.absoluteUpdate(inventory);
    }


}
