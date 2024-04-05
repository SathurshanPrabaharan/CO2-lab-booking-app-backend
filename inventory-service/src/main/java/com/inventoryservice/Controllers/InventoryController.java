package com.inventoryservice.Controllers;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Services.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        super();
        this.inventoryService = inventoryService;
    }

    @PostMapping("/api/v1/inventories")
    public ResponseEntity<Inventory> saveEmployee(@RequestBody Inventory inventory){
        return new ResponseEntity<Inventory>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
    }

}
