package com.inventoryservice.Controllers;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController

public class InventoryController {

    private InventoryService inventoryService;
    private InventoryRepository inventoryRepository;


    public InventoryController(InventoryService inventoryService) {
        super();
        this.inventoryService = inventoryService;
    }

    @PostMapping("/api/v1/inventories")
    public ResponseEntity<Inventory> saveEmployee(@RequestBody Inventory inventory){
        return new ResponseEntity<Inventory>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
    }
    @RequestMapping("/api/v1/inventories")
    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }


    @GetMapping("/api/v1/inventories/{id}")
    public ResponseEntity<Inventory> getInventorybyId(@PathVariable UUID id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));
        return ResponseEntity.ok(inventory);
    }

}
