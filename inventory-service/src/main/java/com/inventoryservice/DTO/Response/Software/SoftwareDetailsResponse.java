package com.inventoryservice.DTO.Response.Software;
//used to represent the response sent back to the client when retrieving details about a software entity
import com.inventoryservice.Models.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public ResponseSoftwareDetails(Software software) {
        this.id = software.getId();
        this.name = software.getName();
        this.version = software.getVersion();
    }

}
