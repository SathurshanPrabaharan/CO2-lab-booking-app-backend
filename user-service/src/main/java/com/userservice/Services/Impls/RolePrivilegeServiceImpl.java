package com.userservice.Services.Impls;


import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeArchiveRequest;
import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeCreateRequest;
import com.userservice.DTO.Request.RolePrivilege.RolePrivilegeUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.RolePrivilege;
import com.userservice.Repositories.RolePrivilegeRepository;
import com.userservice.Services.RolePrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;


    @Override
    public RolePrivilege saveRolePrivilege(RolePrivilegeCreateRequest request) {

        RolePrivilege existingRolePrivilege = rolePrivilegeRepository.findByKey(request.getKey()).orElse(null);

        if (existingRolePrivilege == null) {
            RolePrivilege rolePrivilege = RolePrivilege.builder()
                    .id(UUID.randomUUID())
                    .key(request.getKey())
                    .title(request.getTitle())
                    .createdBy(request.getCreatedBy())
                    .status(STATUS.ACTIVE)
                    .build();

            return rolePrivilegeRepository.save(rolePrivilege);

        } else {
            throw new RuntimeException(" '" + request.getKey() + "' is  already exist. ");
        }
    }

    @Override
    public List<RolePrivilege> getAllRolePrivileges() {
        return rolePrivilegeRepository.findAll();
    }


    @Override
    public List<RolePrivilege> getAllRolePrivileges(UUID createdBy, STATUS status) {
        if (createdBy == null && status == null) {
            return rolePrivilegeRepository.findAll();
        }

        if (createdBy != null && status != null) {
            return rolePrivilegeRepository.findByCreatedByAndStatus(createdBy, status);
        } else if (createdBy != null) {
            return rolePrivilegeRepository.findByCreatedBy(createdBy);
        } else {
            return rolePrivilegeRepository.findByStatus(status);
        }
    }


    @Override
    public RolePrivilege findById(UUID id) {
        Optional<RolePrivilege> rolePrivilegeOptional = rolePrivilegeRepository.findById(id);
        return rolePrivilegeOptional.orElseThrow(() -> new ResourceNotFoundException("RolePrivilege not found with id : " + id));
    }




    @Override
    public RolePrivilege updateRolePrivilege(UUID id, RolePrivilegeUpdateRequest request) {


        RolePrivilege existingRolePrivilege = findById(id);

        RolePrivilege updatedRolePrivilege = RolePrivilege.builder()
                .id(existingRolePrivilege.getId())
                .key(existingRolePrivilege.getKey())
                .title(request.getTitle())
                .createdBy(existingRolePrivilege.getCreatedBy())
                .createdAt(existingRolePrivilege.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.valueOf(request.getStatus().toUpperCase()))
                .build();

        return  rolePrivilegeRepository.save(updatedRolePrivilege);

    }


    @Override
    public void archiveRolePrivilege(UUID id, RolePrivilegeArchiveRequest request) {

        RolePrivilege existingRolePrivilege = findById(id);

        if(existingRolePrivilege.getStatus()==STATUS.ARCHIVED){
            throw new RuntimeException("Invalid : RolePrivilege already archived");
        }

        RolePrivilege archivedRolePrivilege = RolePrivilege.builder()
                .id(existingRolePrivilege.getId())
                .key(existingRolePrivilege.getKey())
                .title(existingRolePrivilege.getTitle())
                .createdBy(existingRolePrivilege.getCreatedBy())
                .createdAt(existingRolePrivilege.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .build();

        rolePrivilegeRepository.save(archivedRolePrivilege);
    }

}

