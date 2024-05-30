package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.DTO.Response.ResponseMessage;
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
import java.util.stream.Collectors;

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
        SoftwareDetailsResponse response = new SoftwareDetailsResponse (message, updateSoftware);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public List<SoftwareDetailsResponse> getAllSoftware() {
        List<Software> softwareList= softwareService.getAllSoftwares();
        return softwareList.stream()
                .map(software -> new SoftwareDetailsResponse("List of all software", software))
                .collect(Collectors.toList());
    }



}