package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryArchiveRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.DTO.Response.Inventory.InventoryCreatedResponse;
import com.inventoryservice.DTO.Response.Inventory.InventoryDetailsResponse;
import com.inventoryservice.DTO.Response.Inventory.InventoryListResponse;
import com.inventoryservice.DTO.Response.ResponseMessage;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
@Tag(name = "Inventory Controller", description = "Endpoints for inventories")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        super();
        this.inventoryService = inventoryService;
    }
    @Operation(summary = "Create Inventory", description = "Create Inventory with appropriate softwares")
    @PostMapping
    public ResponseEntity<Object> saveInventory(@RequestBody @Valid InventoryCreateRequest inventory) {
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        String message = "Inventory created successfully";
        InventoryCreatedResponse response = new InventoryCreatedResponse(message, savedInventory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Inventories", description = "Get All Inventory by applying appropriate filters")
    @GetMapping
    public ResponseEntity<Object> filterInventory(
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String processor,
            @RequestParam(required = false) String memoryType,
            @RequestParam(required = false) String memorySize,
            @RequestParam(required = false) String storageType,
            @RequestParam(required = false) String storageSize,
            @RequestParam(required = false) String operatingSystem,
            @RequestParam(required = false) STATUS status,
            @RequestParam(required = false) LocalDate startWarrantyExpiryDate, @RequestParam(required = false) LocalDate endWarrantyExpiryDate,
            @RequestParam(required = false) LocalDate startNextMaintenanceDate, @RequestParam(required = false) LocalDate endNextMaintenanceDate,
            @RequestParam(required = false) LocalDate startLastMaintenanceDate, @RequestParam(required = false) LocalDate endLastMaintenanceDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID softwareId

            ){

        Page<Inventory> filteredInventory = inventoryService.filterInventory(manufacturer,model,processor,memoryType,memorySize,storageType,storageSize
        ,operatingSystem,status,startWarrantyExpiryDate,endWarrantyExpiryDate,startNextMaintenanceDate,endNextMaintenanceDate,
                startLastMaintenanceDate,endLastMaintenanceDate,page-1,size,softwareId);
        String message = "Inventories fetched successfully";
        InventoryListResponse response = new InventoryListResponse(message,filteredInventory);
        return new ResponseEntity<>(response,HttpStatus.OK);


    }



    //Get inventory details using id
    @Operation(summary = "Get Inventories using id", description = "Get  Inventory by using inventory id")
    @GetMapping("{id}")
    public ResponseEntity<Object> getInventoryDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Inventory inventory = inventoryService.findById(id);
        String message = "Inventory details fetched successfully";
        InventoryDetailsResponse response = new InventoryDetailsResponse(message, inventory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Update Inventory using id", description = "Update the inventory with od")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateInventory(@PathVariable UUID id, @RequestBody @Valid InventoryUpdateRequest request) throws ResourceNotFoundException {
        Inventory updateInventory = inventoryService.updateInventory(id, request);
        String message = "Inventory updated successfully";
        InventoryDetailsResponse response = new InventoryDetailsResponse(message, updateInventory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //Delete done with response msg
    @Operation(summary = "archived Inventories", description = "Delete Inventory by using inventory id")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveInventory(@PathVariable UUID id, @RequestBody @Valid InventoryArchiveRequest request) throws ResourceNotFoundException {
        inventoryService.archiveInventory(id, request);
        String message = "Inventory archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

