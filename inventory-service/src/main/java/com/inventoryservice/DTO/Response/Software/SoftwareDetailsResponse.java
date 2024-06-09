package com.inventoryservice.DTO.Response.Software;
//used to represent the response sent back to the client when retrieving details about a software entity
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Models.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SoftwareDetailsResponse {
    private String message;
    private ResponseSoftwareDetails data;


    public SoftwareDetailsResponse(String message, Software foundedSoftware) {
        this.message = message;
        this.data = new ResponseSoftwareDetails(foundedSoftware);
    }

}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseSoftwareDetails {
    private UUID id;
    private String name;
    private String version;
    private String description;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private STATUS status;

    public ResponseSoftwareDetails(Software software) {
        this.id = software.getId();
        this.name = software.getName();
        this.version = software.getVersion();
        this.description = software.getDescription();
        this.category = software.getCategory();
        this.createdAt = software.getCreatedAt();
        this.updatedAt = software.getUpdatedAt();
        this.createdBy = software.getCreatedBy();
        this.updatedBy = software.getUpdatedBy();
        this.status = software.getStatus();
    }

}
