package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SoftwareService {
    List<Software> getAllSoftwares();

    Software findById(UUID id);

    Software saveSoftware(SoftwareCreateRequest softwareCreateRequest);

    Software updateSoftware(UUID id, SoftwareUpdateRequest softwareUpdateRequest) throws InventoryNotFoundException;
    Page<Software> filterSoftware(String name, String version, int page, int size);




}
