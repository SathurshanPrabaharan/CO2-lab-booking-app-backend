package com.inventoryservice.DTO.Request.Inventory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class InventoryDeleteRequest {
    @NotNull(message = "Invalid DeletedBy: DeletedBy cannot be null")
    private Long deletedBy;

}
