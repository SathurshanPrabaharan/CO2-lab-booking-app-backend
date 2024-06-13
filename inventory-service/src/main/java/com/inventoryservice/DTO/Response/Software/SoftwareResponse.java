package com.inventoryservice.DTO.Response.Software;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SoftwareResponse {
    private String message;
    private Software data;
}
