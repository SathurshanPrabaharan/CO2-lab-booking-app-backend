package com.inventoryservice.DTO.Response.Software;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedSoftware {

    private UUID id;
    private String name;
    private String version;




    public ModifiedSoftware (Software software){
        this.id=software.getId();
        this.name=software.getName();
        this.version=software.getVersion();

    }


}

