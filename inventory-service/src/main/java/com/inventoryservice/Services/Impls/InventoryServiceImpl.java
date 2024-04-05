package com.inventoryservice.Services.Impls;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {


    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        super();
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    }