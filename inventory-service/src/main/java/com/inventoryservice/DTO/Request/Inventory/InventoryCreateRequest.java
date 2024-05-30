package com.inventoryservice.DTO.Request.Inventory;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.inventoryservice.Enums.STATUS;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* DTO is used when a new inventory user is being created.
* */
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class InventoryCreateRequest {
    @NotEmpty(message = "Invalid Name: Name cannot be empty")
    private String name;
    @Size(max = 50, message = "Serial Number can be at most 50 characters")
    private String serialNum;
    @Size(max = 100, message = "Manufacturer can be at most 100 characters")
    private String manufacturer;
    @Size(max = 50, message = "Model can be at most 50 characters")
    private String model;
    @Size(max = 50, message = "Processor can be at most 50 characters")
    private String processor;
    @Size(max = 20, message = "Memory Type can be at most 20 characters")
    private String memoryType;
    @Size(max = 20, message = "Memory Size can be at most 20 characters")
    private String memorySize;
    @Size(max = 20, message = "Storage Type can be at most 20 characters")
    private String storageType;
    @Size(max = 20, message = "Storage Size can be at most 20 characters")
    private String storageSize;
    @Size(max = 50, message = "Operating System can be at most 50 characters")
    private String operatingSystem;
    @PastOrPresent(message = "Invalid Purchase Date: Purchase Date cannot be in the future")
    private LocalDate purchaseDate;
    @DecimalMin(value = "0.0", inclusive = false, message = "Invalid Purchase Cost: Purchase Cost must be greater than 0")
    private Float purchaseCost;
    @FutureOrPresent(message = "Invalid Warranty Expiry Date: Warranty Expiry Date cannot be in the past")
    private LocalDate warrantyExpiry;
    @Size(max = 100, message = "Short Note can be at most 100 characters")
    private String shortNote;
    @PastOrPresent(message = "Invalid Last Maintenance Date: Last Maintenance Date cannot be in the future")
    private LocalDate lastMaintenanceDate;
    @Future(message = "Invalid Next Maintenance Date: Next Maintenance Date must be in the future")
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Invalid CreatedBy: CreatedBy cannot be null")
    private Long createdBy;
    private List<UUID> installedSoftwaresID;
}

