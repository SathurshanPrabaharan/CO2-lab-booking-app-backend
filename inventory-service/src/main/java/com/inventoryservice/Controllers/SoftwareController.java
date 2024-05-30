package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.DTO.Response.Software.SoftwareDetailsResponse;
import com.inventoryservice.DTO.Response.Software.SoftwareResponse;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.SoftwareRepository;
import com.inventoryservice.Services.SoftwareService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<Object> saveSoftware(@RequestBody @Valid SoftwareCreateRequest software) {
        Software saveSoftware = softwareService.saveSoftware(software);
        String message = "Software created successfully";
        SoftwareResponse response = new SoftwareResponse(message, saveSoftware);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateSoftware(@PathVariable UUID id, @RequestBody @Valid SoftwareUpdateRequest softwareUpdateRequest) throws InventoryNotFoundException {
        Software updateSoftware = softwareService.updateSoftware(id, softwareUpdateRequest);
        String message = "Software updated successfully";
        SoftwareDetailsResponse response = new SoftwareDetailsResponse(message, updateSoftware);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllSoftware(
            @RequestParam(required = false)String name,
            @RequestParam(required = false) String version)
    {
        List<Software> foundedSoftwares ;
        if(name != null)
        {
            foundedSoftwares = softwareRepository.findByName(name);
        } else if (version != null) {
            foundedSoftwares=softwareRepository.findByVersion(version);
        }else {
            foundedSoftwares = softwareRepository.findAll();
        }
        if (foundedSoftwares.isEmpty()) {
            throw new InventoryNotFoundException("No Softwares found");
        }
        return ResponseEntity.ok(foundedSoftwares);
    }


}
