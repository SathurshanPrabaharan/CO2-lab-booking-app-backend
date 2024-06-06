package com.userservice.Services.Impls;



import com.userservice.DTO.Request.Admin.AdminCreateRequest;
import com.userservice.DTO.Request.Admin.AdminUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Admin;
import com.userservice.Models.SupportModels.Profession;
import com.userservice.Repositories.AdminRepository;
import com.userservice.Services.AdminService;
import com.userservice.Services.ProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ProfessionService professionService;

    @Override
    public Admin saveAdmin( AdminCreateRequest adminCreateRequest) {

        Profession associatedProfession = professionService.findById(adminCreateRequest.getProfessionId());



        Admin admin = Admin.builder()
                .id(UUID.randomUUID())
                .name(adminCreateRequest.getName())
                .email(adminCreateRequest.getEmail())
                .password(adminCreateRequest.getPassword())
                .createdBy(adminCreateRequest.getCreatedBy())
                .profession(associatedProfession)
                .status(STATUS.ACTIVE)
                .build();

        return  adminRepository.save(admin);
    }



    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    @Override
    public List<Admin> getAllAdmins(String name, UUID createdBy, STATUS status) {
        if (name == null && createdBy == null && status == null) {
            return adminRepository.findAll();
        }

        if (name != null && createdBy != null && status != null) {
            return adminRepository.findByNameAndCreatedByAndStatus(name, createdBy, status);
        } else if (name != null && createdBy != null) {
            return adminRepository.findByNameAndCreatedBy(name, createdBy);
        } else if (name != null && status != null) {
            return adminRepository.findByNameAndStatus(name, status);
        } else if (createdBy != null && status != null) {
            return adminRepository.findByCreatedByAndStatus(createdBy, status);
        } else if (name != null) {
            return adminRepository.findByName(name);
        } else if (createdBy != null) {
            return adminRepository.findByCreatedBy(createdBy);
        } else {
            return adminRepository.findByStatus(status);
        }
    }


    @Override
    public Admin findById(UUID id) {
        Optional<Admin> AdminOptional = adminRepository.findById(id);
        return AdminOptional.orElseThrow(() -> new ResourceNotFoundException("admin not found with id : " + id));
    }

    @Override
    public Admin updateAdmin(UUID id, AdminUpdateRequest adminUpdateRequest) {


        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(
                    () -> new ResourceNotFoundException("admin not found with id : " + id)
                );

        Profession associatedProfession = professionService.findById(adminUpdateRequest.getProfessionId());

        Admin updatedAdmin = Admin.builder()
                .id(existingAdmin.getId())
                .name(adminUpdateRequest.getName())
                .email(existingAdmin.getEmail())
                .password(existingAdmin.getPassword())
                .profession(associatedProfession)
                .createdBy(existingAdmin.getCreatedBy())
                .createdAt(existingAdmin.getCreatedAt())
                .updatedBy(adminUpdateRequest.getUpdatedBy())
                .status(STATUS.valueOf(adminUpdateRequest.getStatus().toUpperCase()))
                .build();

        return  adminRepository.save(updatedAdmin);

    }


    @Override
    public void archiveAdmin(UUID id) {

        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("admin not found with id : " + id)
                );
        if(existingAdmin.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Admin already archived");
        }

        Admin archivedAdmin = Admin.builder()
                .id(existingAdmin.getId())
                .name(existingAdmin.getName())
                .email(existingAdmin.getEmail())
                .password(existingAdmin.getPassword())
                .profession(existingAdmin.getProfession())
                .createdBy(existingAdmin.getCreatedBy())
                .createdAt(existingAdmin.getCreatedAt())
                .updatedBy(existingAdmin.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .build();

        adminRepository.save(archivedAdmin);
    }

}
