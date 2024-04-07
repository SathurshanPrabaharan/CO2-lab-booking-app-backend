package com.inventoryservice.Response;

import com.inventoryservice.Models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponse {
    private Inventory result;
    private String message;

}

