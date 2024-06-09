package com.inventoryservice.DTO.Response.Software;


import com.inventoryservice.Models.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedSoftware {

    private UUID id;
    private String name;
    private String version;
    private String category;
    private UUID createdBy;


    public ModifiedSoftware (Software software){
        this.id=software.getId();
        this.name=software.getName();
        this.version=software.getVersion();
        this.category=software.getCategory();
        this.createdBy=software.getCreatedBy();

    }


}

