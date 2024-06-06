package com.inventoryservice.DTO.Response.Inventory;

import com.inventoryservice.Models.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponse {

        private String message;
        private Inventory data;

}
