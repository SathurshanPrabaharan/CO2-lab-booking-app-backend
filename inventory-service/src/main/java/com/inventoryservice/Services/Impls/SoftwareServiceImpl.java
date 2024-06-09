package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.Software.SoftwareCreateRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareArchiveRequest;
import com.inventoryservice.DTO.Request.Software.SoftwareUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.SoftwareRepository;
import com.inventoryservice.Services.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoftwareServiceImpl implements SoftwareService {
    private final SoftwareRepository softwareRepository;



    @Override
    public Software saveSoftware(SoftwareCreateRequest request) {
        Software software = Software.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .version(request.getVersion())
                .description(request.getDescription())
                .category(request.getCategory())
                .createdBy(request.getCreatedBy())
                .status(STATUS.ACTIVE)
                .build();
        return softwareRepository.save(software);
    }

    @Override
    public Page<Software> filterSoftware(String name, String version, String category, UUID createdBy, String status, int page, int size) {

        List<Software> allSoftwares = softwareRepository.findAllByOrderByCreatedAtDesc();

        List<Software> filteredSoftware = allSoftwares.stream()
                .filter(software -> name == null || (software.getName() != null && software.getName().equalsIgnoreCase(name)))
                .filter(software -> version == null || (software.getVersion() != null && software.getVersion().equalsIgnoreCase(version)))
                .filter(software -> category == null || (software.getCategory() != null && software.getCategory().equalsIgnoreCase(category)))
                .filter(software -> createdBy == null || (software.getCreatedBy() != null && software.getCreatedBy().equals(createdBy)))
                .filter(software -> status == null || software.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        int totalSize = filteredSoftware.size();
        int start = Math.min(page * size, totalSize);
        int end = Math.min((page + 1) * size, totalSize);

        List<Software> paginatedList = filteredSoftware.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), totalSize);
    }


    @Override
    public List<Software> getAllSoftwares() {
        return softwareRepository.findAll();
    }

    @Override
    public Software findById(UUID id) {
        Optional<Software> softwareOptional = softwareRepository.findById(id);
        return softwareOptional.orElseThrow(() -> new ResourceNotFoundException("Software not found with id : " + id));
    }


    @Override
    public Software updateSoftware(UUID id, SoftwareUpdateRequest request) {
        Software existingSoftware = softwareRepository.findById(id)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Software not found with id : "+id)
                );

        //set the values
        existingSoftware.setName(request.getName());
        existingSoftware.setVersion(request.getVersion());
        existingSoftware.setDescription(request.getDescription());
        existingSoftware.setCategory(request.getCategory());
        existingSoftware.setUpdatedBy(request.getUpdatedBy());
        existingSoftware.setStatus(STATUS.valueOf(request.getStatus().toUpperCase()));
        existingSoftware.setUpdatedAt(LocalDateTime.now());


        return softwareRepository.save(existingSoftware);

    }




    @Override
    public void archiveSoftware(UUID id, SoftwareArchiveRequest request) {

        Software existingSoftware = findById(id);

        if(existingSoftware.getStatus() == STATUS.ARCHIVED){
            throw new IllegalArgumentException("Invalid : Booking already archived");
        }

        // set values
        existingSoftware.setStatus(STATUS.ARCHIVED);
        existingSoftware.setUpdatedBy(request.getArchivedBy());
        existingSoftware.setUpdatedAt(LocalDateTime.now());


        softwareRepository.save(existingSoftware);
    }
}
