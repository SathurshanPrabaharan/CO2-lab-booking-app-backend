package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.InventoryRequest;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        super();
        this.inventoryRepository = inventoryRepository;
    }

  /*  @Override
    public Inventory saveInventory(Inventory inventory) {

        return inventoryRepository.save(inventory);
    }
*/
    //save the user object to database
    public Inventory saveInventory(InventoryRequest inventoryRequest)
    {


        Inventory inventory =Inventory.build(UUID.randomUUID(),inventoryRequest.getName(),inventoryRequest.getSerialNum(),inventoryRequest.getManufacturer(),inventoryRequest.getModel(),
                                             inventoryRequest.getProcessor(),inventoryRequest.getMemoryType(),inventoryRequest.getMemorySize(),inventoryRequest.getStorageType(),
                                             inventoryRequest.getStorageSize(),inventoryRequest.getOperatingSystem(),inventoryRequest.getStatus(),inventoryRequest.getPurchaseDate(),
                                             inventoryRequest.getPurchaseCost(),inventoryRequest.getWarrantyExpiry(),inventoryRequest.getShortNote(),inventoryRequest.getLastMaintenanceDate(),
                                             inventoryRequest.getNextMaintenanceDate(),inventoryRequest.getCreatedAt(),inventoryRequest.getUpdatedAt(),inventoryRequest.getCreatedBy(),
                                             inventoryRequest.getInstalledSoftwares());
        return  inventoryRepository.save(inventory);
    }

    public Inventory getInventory(UUID id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(()->new InventoryNotFoundException("Inventory not found with ID: " + id));
        return inventory;

    }

    public Inventory updateInventory(UUID id, Inventory inventoryDetails) throws InventoryNotFoundException {

        //String message = "Inventory  posted successfully";


        Inventory updateInventory = inventoryRepository.findById(id)
                .orElseThrow(()->new InventoryNotFoundException("Inventory not found with ID: " + id));

        if(inventoryDetails.getName() !=null)
        {
            updateInventory.setName(inventoryDetails.getName());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setSerialNum(inventoryDetails.getSerialNum());
        }
        if(inventoryDetails.getManufacturer() != null)
        {
            updateInventory.setManufacturer(inventoryDetails.getManufacturer());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setModel(inventoryDetails.getModel());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setProcessor(inventoryDetails.getProcessor());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setMemoryType(inventoryDetails.getMemoryType());
        }
        if(inventoryDetails.getMemorySize() !=null)
        {
            updateInventory.setMemorySize(inventoryDetails.getMemorySize());
        }
        if(inventoryDetails.getStorageType() !=null)
        {
            updateInventory.setStorageType(inventoryDetails.getStorageType());
        }
        if(inventoryDetails.getStorageSize() !=null)
        {
            updateInventory.setStorageSize(inventoryDetails.getStorageSize());
        }
        if(inventoryDetails.getOperatingSystem() !=null)
        {
            updateInventory.setOperatingSystem(inventoryDetails.getOperatingSystem());

        }
        if(inventoryDetails.getPurchaseDate() !=null)
        {
            updateInventory.setPurchaseDate(inventoryDetails.getPurchaseDate());

        }
        if(inventoryDetails.getPurchaseCost() !=null)
        {
            updateInventory.setPurchaseCost(inventoryDetails.getPurchaseCost());

        }
        if(inventoryDetails.getWarrantyExpiry() !=null){
            updateInventory.setWarrantyExpiry(inventoryDetails.getWarrantyExpiry());
        }
        if(inventoryDetails.getShortNote() !=null){
            updateInventory.setShortNote(inventoryDetails.getShortNote());

        }
        if(inventoryDetails.getLastMaintenanceDate() !=null){
            updateInventory.setLastMaintenanceDate(inventoryDetails.getLastMaintenanceDate());

        }
        if(inventoryDetails.getNextMaintenanceDate() !=null){
            updateInventory.setNextMaintenanceDate(inventoryDetails.getNextMaintenanceDate());

        }
        if(inventoryDetails.getCreatedAt() !=null){
            updateInventory.setCreatedAt(inventoryDetails.getCreatedAt());

        }
        if (inventoryDetails.getUpdatedAt() !=null)
        {
            updateInventory.setUpdatedAt(inventoryDetails.getUpdatedAt());

        }
        if(inventoryDetails.getCreatedAt() !=null)
        {
            updateInventory.setCreatedBy(inventoryDetails.getCreatedBy());

        }
        if(inventoryDetails.getInstalledSoftwares() !=null)
        {
            updateInventory.setInstalledSoftwares(inventoryDetails.getInstalledSoftwares());

        }
        inventoryRepository.save(updateInventory);
        //InventoryResponse response = new InventoryResponse(inventoryRepository.save(updateInventory),message);
        return updateInventory;

       // return

    }






}