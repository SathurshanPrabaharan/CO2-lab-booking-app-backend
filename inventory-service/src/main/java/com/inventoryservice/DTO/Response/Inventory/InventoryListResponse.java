package com.inventoryservice.DTO.Response.Inventory;

import com.inventoryservice.Models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

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
    private List<ModifiedInventory> results;

    public ResultInventory(Page<Inventory> results) {
        this.total = results.getTotalElements();
        List<ModifiedInventory> resultModifiedInventories = new ArrayList<>();
        for(Inventory currentInventory : results.getContent()){
            ModifiedInventory temp = new ModifiedInventory(currentInventory);
            resultModifiedInventories.add(temp);
        }
        this.results=resultModifiedInventories;
    }

}
