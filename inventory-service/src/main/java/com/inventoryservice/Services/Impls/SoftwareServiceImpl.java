package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.SoftwareRepository;
import com.inventoryservice.Services.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoftwareServiceImpl implements SoftwareService {
    private final SoftwareRepository softwareRepository;

    @Override
    public List<Software> getAllSoftwares() {
        return softwareRepository.findAll();
    }

    @Override
    public Software findById(UUID id) {
        Optional<Software> softwareOptional = softwareRepository.findById(id);
        return softwareOptional.orElseThrow(() -> new InventoryNotFoundException("Software not found with id : " + id));
    }

    @Override
    public Software saveSoftware(SoftwareCreateRequest softwareCreateRequest) {
        Software software = Software.builder()
                .id(UUID.randomUUID())
                .name(softwareCreateRequest.getName())
                .version(softwareCreateRequest.getVersion())
                .build();
        return softwareRepository.save(software);
    }

    @Override
    public Software updateSoftware(UUID id, SoftwareUpdateRequest softwareUpdateRequest) {
        Software existingSoftware = softwareRepository.findById(id)
                .orElseThrow(
                        ()->new InventoryNotFoundException("Software not found with id : "+id)
                );
        Software updatedSoftware = Software.builder()
                .id(existingSoftware.getId())
                .name(softwareUpdateRequest.getName())
                .version(softwareUpdateRequest.getVersion())
                .build();
        return softwareRepository.save(updatedSoftware);

    }

        @Override
    public Page<Software> filterSoftware(String name, int page, int size) {


        List<Software> allSoftware = softwareRepository.findAll();
        List<Software> filteredSoftware = allSoftware.stream()
                .filter(software -> name == null || (software.getName() != null && software.getName().equals(name)))
                .collect(Collectors.toList());

        int totalSize = filteredSoftware.size();
        int start = Math.min(page * size, totalSize);
        int end = Math.min((page + 1) * size, totalSize);

        List<Software> paginatedList = filteredSoftware.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), totalSize);
    }



}
