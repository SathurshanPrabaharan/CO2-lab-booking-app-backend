package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareDeleteRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.DTO.Response.Software.SoftwareResponse;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Software;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface SoftwareService {
    List<Software> getAllSoftwares();

    Software findById(UUID id
    );

    Software saveSoftware(SoftwareCreateRequest softwareCreateRequest);

    Software updateSoftware(UUID id, SoftwareUpdateRequest softwareUpdateRequest) throws ResourceNotFoundException;
    Page<Software> filterSoftware(String name, int page, int size);

    SoftwareResponse deleteSoftware(UUID id, SoftwareDeleteRequest softwareDeleteRequest)throws ResourceNotFoundException;


}
