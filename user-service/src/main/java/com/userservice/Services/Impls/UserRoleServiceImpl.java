package com.userservice.Services.Impls;


import com.userservice.DTO.Request.UserRole.UserRoleArchiveRequest;
import com.userservice.DTO.Request.UserRole.UserRoleCreateRequest;
import com.userservice.DTO.Request.UserRole.UserRolePatchRequest;
import com.userservice.DTO.Request.UserRole.UserRoleUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.RolePrivilege;
import com.userservice.Models.UserRole;
import com.userservice.Repositories.RolePrivilegeRepository;
import com.userservice.Repositories.UserRoleRepository;
import com.userservice.Services.RolePrivilegeService;
import com.userservice.Services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;

    @Autowired
    private RolePrivilegeService rolePrivilegeService;


    @Override
    public UserRole saveUserRole(UserRoleCreateRequest request) {

        UserRole existingUserRole = userRoleRepository.findByKey(request.getKey()).orElse(null);

        if (existingUserRole == null) {
            UserRole userRole = UserRole.builder()
                    .id(UUID.randomUUID())
                    .key(request.getKey())
                    .title(request.getTitle())
                    .createdBy(request.getCreatedBy())
                    .status(STATUS.ACTIVE)
                    .build();

            // Set privileges to the user role
            if (request.hasPrivilegeList()) {
                request.addCreatedByAndStatusToPrivileges();
                Set<RolePrivilege> privileges = new HashSet<>(request.getPrivilegeList());
                userRole.setPrivilegeSet(privileges);
            } else if (request.hasPrivilegeIDs()) {
                List<RolePrivilege> privileges = rolePrivilegeRepository.findAllById(request.getPrivilegeIDs());
                userRole.setPrivilegeSet(new HashSet<>(privileges));
            }

            return userRoleRepository.save(userRole);

        } else {
            throw new RuntimeException(" '" + request.getKey() + "' is  already exist. ");
        }
    }



    @Override
    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }


    @Override
    public List<UserRole> getAllUserRoles(UUID createdBy, STATUS status) {
        if (createdBy == null && status == null) {
            return userRoleRepository.findAll();
        }

        if (createdBy != null && status != null) {
            return userRoleRepository.findByCreatedByAndStatus(createdBy, status);
        } else if (createdBy != null) {
            return userRoleRepository.findByCreatedBy(createdBy);
        } else {
            return userRoleRepository.findByStatus(status);
        }
    }


    @Override
    public UserRole findById(UUID id) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(id);
        return userRoleOptional.orElseThrow(() -> new ResourceNotFoundException("UserRole not found with id : " + id));
    }




    @Override
    public UserRole updateUserRole(UUID id, UserRoleUpdateRequest request) {


        UserRole existingUserRole = findById(id);

        UserRole updatedUserRole = UserRole.builder()
                .id(existingUserRole.getId())
                .key(existingUserRole.getKey())
                .title(request.getTitle())
                .privilegeSet(existingUserRole.getPrivilegeSet())
                .createdAt(existingUserRole.getCreatedAt())
                .createdBy(existingUserRole.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.valueOf(request.getStatus().toUpperCase()))
                .build();

        return  userRoleRepository.save(updatedUserRole);

    }



    @Override
    public UserRole addUserRolePrivileges(UUID id, UserRolePatchRequest request) {
        // Find the existing user role by ID
        UserRole existingUserRole = findById(id);

        // Create a new user role object based on the existing user role
        UserRole updatedUserRole = UserRole.builder()
                .id(existingUserRole.getId())
                .key(existingUserRole.getKey())
                .title(existingUserRole.getTitle())
                .createdAt(existingUserRole.getCreatedAt())
                .createdBy(existingUserRole.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .status(existingUserRole.getStatus())
                .build();

        // Initialize the privileges set with the existing privileges
        Set<RolePrivilege> updatedPrivileges = new HashSet<>(existingUserRole.getPrivilegeSet());
        Set<String> existingKeys = new HashSet<>();
        for(RolePrivilege privilege: updatedPrivileges){
            existingKeys.add(privilege.getKey());
        }

        // If request has privileges, add them to the updated privileges set
        if (request.hasPrivilegeList()) {
            for (RolePrivilege privilege : request.getPrivilegeList()) {
                // Check if the privilege already exists in the user role
                while(!existingKeys.contains(privilege.getKey())) {
                    // Check if the privilege already exists in the database
                    if (!rolePrivilegeRepository.existsByKey(privilege.getKey())) {
                        // If privilege doesn't exist, save it to the database
                        privilege.setCreatedBy(request.getUpdatedBy());
                        privilege.setStatus(STATUS.ACTIVE);
                        RolePrivilege savedPrivilege = rolePrivilegeRepository.save(privilege);
                        // Add the saved privilege to the updated privileges set
                        updatedPrivileges.add(savedPrivilege);
                    } else {
                        // If privilege already exists, fetch and add it to the updated privileges set directly
                        RolePrivilege existPrivilege = rolePrivilegeRepository.findByKey(privilege.getKey())
                                .orElseThrow(
                                        () -> new ResourceNotFoundException("Role Privilege not found with key : " + privilege.getKey())
                                );
                        updatedPrivileges.add(privilege);

                    }
                    //add key to set
                    existingKeys.add(privilege.getKey());
                }
            }
        }
        // If request has privilege IDs, fetch them and add to the updated privileges set
        else if (request.hasPrivilegeIDs()) {
            List<RolePrivilege> privileges = rolePrivilegeRepository.findAllById(request.getPrivilegeIDs());
            updatedPrivileges.addAll(privileges);
        }

        // Set the updated privileges set to the updated user role
        updatedUserRole.setPrivilegeSet(updatedPrivileges);

        // Save and return the updated user role
        return userRoleRepository.save(updatedUserRole);
    }

    @Override
    public void archiveUserRole(UUID id, UserRoleArchiveRequest request) {

        UserRole existingUserRole = findById(id);

        if(existingUserRole.getStatus()==STATUS.ARCHIVED){
            throw new RuntimeException("Invalid : UserRole already archived");
        }

        UserRole archivedUserRole = UserRole.builder()
                .id(existingUserRole.getId())
                .key(existingUserRole.getKey())
                .title(existingUserRole.getTitle())
                .privilegeSet(existingUserRole.getPrivilegeSet())
                .createdAt(existingUserRole.getCreatedAt())
                .createdBy(existingUserRole.getCreatedBy())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .build();

        userRoleRepository.save(archivedUserRole);
    }


    @Override
    public RolePrivilege removeUserRolePrivilege(UUID userRoleId, UUID privilegeId, UserRoleArchiveRequest request) {
        // Find the userRole by ID
        UserRole userRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new ResourceNotFoundException("UserRole not found with id: " + userRoleId));

        // Find the privilege by ID
        Optional<RolePrivilege> privilegeToRemoveOptional = userRole.getPrivilegeSet()
                .stream()
                .filter(privilege -> privilege.getId().equals(privilegeId))
                .findFirst();

        if (privilegeToRemoveOptional.isPresent()) {
            // Remove the privilege from the userRole's privileges set
            RolePrivilege privilegeToRemove = privilegeToRemoveOptional.get();
            userRole.getPrivilegeSet().remove(privilegeToRemove);

            // Add UpdatedBy
            userRole.setUpdatedBy(request.getUpdatedBy());

            // Save the updated userRole
            userRoleRepository.save(userRole);

            return privilegeToRemove;

        } else {
            throw new ResourceNotFoundException("Privilege not found with id: " + privilegeId);
        }
    }
}

