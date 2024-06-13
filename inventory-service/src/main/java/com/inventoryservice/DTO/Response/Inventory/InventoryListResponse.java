package com.inventoryservice.DTO.Response.Inventory;

import com.inventoryservice.Models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryListResponse {
    private String message;
    private ResultInventory data;

    public InventoryListResponse(String message, Page<Inventory> filteredInventory) {
        this.message = message;
        this.data = new ResultInventory(filteredInventory);
    }
}
@AllArgsConstructor
@NoArgsConstructor
@Data
class ResultInventory{
    private long total;
    private Set<ModifiedInventorySimple> results;

    public ResultInventory(Page<Inventory> results) {
        this.total = results.getTotalElements();
        Set<ModifiedInventorySimple> resultModifiedInventories = new HashSet<>();
        for(Inventory currentInventory : results.getContent()){
            ModifiedInventorySimple temp = new ModifiedInventorySimple(currentInventory);
            resultModifiedInventories.add(temp);
        }
        this.results=resultModifiedInventories;
    }

}
