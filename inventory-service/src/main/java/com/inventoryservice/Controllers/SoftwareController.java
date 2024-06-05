package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.DTO.Response.Software.SoftwareDetailsResponse;
import com.inventoryservice.DTO.Response.Software.SoftwareListResponse;
import com.inventoryservice.DTO.Response.Software.SoftwareResponse;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.SoftwareRepository;
import com.inventoryservice.Services.SoftwareService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/software")
public class SoftwareController {
    @Autowired
    private SoftwareRepository softwareRepository;
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
        SoftwareResponse response = new SoftwareResponse(message, saveSoftware);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @Operation(summary = "Update Software", description = "Update the software")

    @PutMapping("{id}")
    public ResponseEntity<Object> updateSoftware(@PathVariable UUID id, @RequestBody @Valid SoftwareUpdateRequest softwareUpdateRequest) throws InventoryNotFoundException {
        Software updateSoftware = softwareService.updateSoftware(id, softwareUpdateRequest);
        String message = "Software updated successfully";
        SoftwareDetailsResponse response = new SoftwareDetailsResponse(message, updateSoftware);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get Softwares", description = "Get All Software by applying appropriate filters")
    @GetMapping
    public ResponseEntity<Object> filterSoftware(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String version,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {
        // Validate page and size parameters
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Page index must not be less than zero");
        }
        if (size <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Size must be greater than zero");
        }
        Page<Software> filteredSoftware = softwareService.filterSoftware(name,version,page,size);

        String message = "Softwares fetched successfully";
        SoftwareListResponse response = new SoftwareListResponse(message, filteredSoftware);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
