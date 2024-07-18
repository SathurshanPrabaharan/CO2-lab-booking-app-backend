package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareArchiveRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.DTO.Response.ResponseMessage;
import com.inventoryservice.DTO.Response.Software.SoftwareCreatedResponse;
import com.inventoryservice.DTO.Response.Software.SoftwareDetailsResponse;
import com.inventoryservice.DTO.Response.Software.SoftwareListResponse;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Services.SoftwareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventories/softwares")
@Tag(name = "Software Controller", description = "Endpoints for softwares")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    public SoftwareController(SoftwareService softwareService) {
        super();
        this.softwareService = softwareService;
    }
    @Operation(summary = "Create Software", description = "Create new software")
    @PostMapping
    public ResponseEntity<Object> saveSoftware(@RequestBody @Valid SoftwareCreateRequest software) {
        Software saveSoftware = softwareService.saveSoftware(software);
        String message = "Software created successfully";
        SoftwareCreatedResponse response = new SoftwareCreatedResponse(message, saveSoftware);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "Get Software List", description = "Get All Software by applying appropriate filters")
    @GetMapping
    public ResponseEntity<Object> filterSoftware(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String version,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) UUID createdBy,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {

        Page<Software> filteredSoftware = softwareService.filterSoftware(name,version,category, createdBy,status,page-1,size);

        String message = "Softwares fetched successfully";
        SoftwareListResponse response = new SoftwareListResponse(message, filteredSoftware);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @Operation(summary = "Get Software using id", description = "Create new software")
    @GetMapping("{id}")
    public ResponseEntity<Object> getSoftwareDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Software software = softwareService.findById(id);
        String message = "Software details fetched successfully";
        SoftwareDetailsResponse response = new SoftwareDetailsResponse(message, software);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Update Software", description = "Update the software using id")
    @PutMapping("{id}")
    public ResponseEntity<Object> updateSoftware(@PathVariable UUID id, @RequestBody @Valid SoftwareUpdateRequest softwareUpdateRequest) throws ResourceNotFoundException {
        Software updateSoftware = softwareService.updateSoftware(id, softwareUpdateRequest);
        String message = "Software updated successfully";
        SoftwareDetailsResponse response = new SoftwareDetailsResponse(message, updateSoftware);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @Operation(summary = "Archive Software", description = "Archive the software -> Status=ARCHIVED")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveSoftware(@PathVariable UUID id, @RequestBody @Valid SoftwareArchiveRequest softwareDeleteRequest) throws ResourceNotFoundException {
        softwareService.archiveSoftware(id, softwareDeleteRequest);
        String message = "Software archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }






}
