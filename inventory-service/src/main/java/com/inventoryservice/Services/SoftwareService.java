package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareArchiveRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.DTO.Response.Software.SoftwareResponse;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Software;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface SoftwareService {

    Software saveSoftware(SoftwareCreateRequest softwareCreateRequest);

    Page<Software> filterSoftware(String name, String version, String category, UUID createdBy, String status, int page, int size);

    List<Software> getAllSoftwares();

    Software findById(UUID id);


    Software updateSoftware(UUID id, SoftwareUpdateRequest softwareUpdateRequest) throws ResourceNotFoundException;

    void archiveSoftware(UUID id, SoftwareArchiveRequest softwareDeleteRequest)throws ResourceNotFoundException;


}
