package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.SoftwareRepository;
import com.inventoryservice.Services.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class SoftwareServiceImpl implements SoftwareService {
    private final SoftwareRepository softwareRepository;

    @Override
    public List<Software> getAllSoftwares() {
        return null;
    }

    @Override
    public Software findById(UUID id) {
        Optional<Software> softwareOptional = softwareRepository.findById(id);
        return softwareOptional.orElseThrow(() -> new InventoryNotFoundException("Software not found with id : " + id));
    }

    @Override
    public Software saveSoftware(SoftwareCreateRequest softwareCreateRequest) {
        return null;
    }

    @Override
    public Software updateSoftware(UUID id, SoftwareUpdateRequest softwareUpdateRequest) throws InventoryNotFoundException {
        return null;
    }

}
