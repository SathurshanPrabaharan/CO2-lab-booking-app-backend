package com.userservice.Services.Impls;

import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.userservice.DTO.Request.Admin.AdminArchiveRequest;
import com.userservice.DTO.Request.Admin.AdminCreateRequest;
import com.userservice.DTO.Request.Admin.AdminUpdateRequest;
import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Admin;
import com.userservice.Models.SupportModels.Department;
import com.userservice.Models.SupportModels.Profession;
import com.userservice.Models.UserRole;
import com.userservice.Repositories.AdminRepository;
import com.userservice.Repositories.SupportRepositories.DepartmentRepository;
import com.userservice.Repositories.SupportRepositories.ProfessionRepository;
import com.userservice.Repositories.UserRoleRepository;
import com.userservice.Services.AzureAdService;
import com.userservice.Services.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private AzureAdService azureAdService;
    private final AdminRepository adminRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProfessionRepository professionRepository;
    private final DepartmentRepository departmentRepository;


    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,
                            UserRoleRepository userRoleRepository,
                            ProfessionRepository professionRepository,
                            DepartmentRepository departmentRepository) {
        this.adminRepository = adminRepository;
        this.userRoleRepository = userRoleRepository;
        this.professionRepository = professionRepository;
        this.departmentRepository = departmentRepository;
    }


    @Value("${azure.ad.client-id}")
    private String clientId;

    @Value("${azure.ad.client-secret}")
    private String clientSecret;

    @Value("${azure.ad.tenant-id}")
    private String tenantId;



    @PostConstruct
    private void initializeAzureAdService() {
        this.azureAdService = new AzureAdService(clientId, clientSecret, tenantId);
    }

    @Override
    @Transactional
    public Admin saveAdmin(AdminCreateRequest request) {

        Admin existingAdmin = adminRepository.findByUserPrincipalName(request.getUserPrincipalName()).orElse(null);

        if (existingAdmin != null) {
            throw new IllegalArgumentException("'" + request.getUserPrincipalName() + "' already exists.");
        }

        // Create Azure AD user
        User newUser = mapAdminCreateRequestToUser(request);
        User createdAzureAdUser = azureAdService.createUser(newUser);

        if (createdAzureAdUser == null || createdAzureAdUser.id == null) {
            throw new RuntimeException("Failed to create Azure AD user!");
        }

        // Create Admin in Database
        Admin admin = Admin.builder()
                .id(UUID.randomUUID())
                .objectId(UUID.fromString(createdAzureAdUser.id))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .mobile(request.getMobile())
                .gender(GENDER.valueOf(request.getGender().toUpperCase()))
                .userPrincipalName(request.getUserPrincipalName())
                .contact_email(request.getContact_email())
                .photoUrl(request.getPhotoUrl())
                .createdBy(request.getCreatedBy())
                .accountEnabled(true)
                .status(STATUS.ACTIVE)
                .build();

        // Set UserRole
        UserRole userRole = userRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + request.getUserRoleId()));
        admin.setUserRole(userRole);

        // Set Profession if exists
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id: " + request.getProfessionId()));
            admin.setProfession(profession);
        }

        // Set Department if exists
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
            admin.setDepartment(department);
        }

        return adminRepository.save(admin);
    }


    @Override
    public Admin findById(UUID id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        return adminOptional.orElseThrow(() -> new ResourceNotFoundException("Admin not found with id : " + id));
    }



    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    @Override
    public Page<Admin> filterAdmin(UUID userRoleId, UUID professionId, UUID departmentId,  String gender, String status, UUID createdBy, int page, int size) {
        // Fetch all admins ordered by createdAt in descending order
        List<Admin> allAdmins = adminRepository.findAllByOrderByCreatedAtDesc();

        // Apply filters
        List<Admin> filteredAdmins = allAdmins.stream()
                .filter(admin -> userRoleId == null || (admin.getUserRole() != null && admin.getUserRole().getId().equals(userRoleId)))
                .filter(admin -> professionId == null || (admin.getProfession() != null && admin.getProfession().getId().equals(professionId)))
                .filter(admin -> departmentId == null || (admin.getDepartment() != null && admin.getDepartment().getId().equals(departmentId)))
                .filter(admin -> gender == null || admin.getGender().toString().equalsIgnoreCase(gender))
                .filter(admin -> createdBy == null || admin.getCreatedBy().equals(createdBy))
                .filter(admin -> status == null || admin.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        // Apply pagination
        int start = Math.min(page * size, filteredAdmins.size());
        int end = Math.min((page + 1) * size, filteredAdmins.size());
        List<Admin> paginatedList = filteredAdmins.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), filteredAdmins.size());
    }

    @Override
    public Admin updateAdmin(UUID id, AdminUpdateRequest request) {


        // Call the above Get Details Method
        Admin existingAdmin = findById(id);

        // Update Azure AD user
        User newUser = mapAdminUpdateRequestToUser(request);
        User updatedAzureAdUser = azureAdService.updateUser(String.valueOf(existingAdmin.getObjectId()),newUser);


        // Update Admin in Database
        Admin updatedAdmin = Admin.builder()
                .id(existingAdmin.getId())
                .objectId(existingAdmin.getObjectId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .mobile(request.getMobile())
                .gender(GENDER.valueOf(request.getGender().toUpperCase()))
                .userPrincipalName(existingAdmin.getUserPrincipalName())
                .contact_email(request.getContact_email())
                .photoUrl(request.getPhotoUrl())
                .isInitalLogged(request.getIsInitalLogged())
                .verifyToken(request.getVerifyToken())
                .tokenIssuedAt(request.getTokenIssuedAt())
                .accountEnabled(existingAdmin.getAccountEnabled())
                .createdBy(existingAdmin.getCreatedBy())
                .createdAt(existingAdmin.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.valueOf(request.getStatus().toUpperCase()))
                .build();

        // Set UserRole
        UserRole userRole = userRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + request.getUserRoleId()));
        updatedAdmin.setUserRole(userRole);

        // Set Profession if exists
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id: " + request.getProfessionId()));
            updatedAdmin.setProfession(profession);
        }

        // Set Department if exists
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
            updatedAdmin.setDepartment(department);
        }


        // Enable the Account in DB
        if(request.isWantToEnableAccount()){
            updatedAdmin.setAccountEnabled(true);
        }

        return  adminRepository.save(updatedAdmin);

    }

    @Override
    public void archiveAdmin(UUID id, AdminArchiveRequest request) {

        // Call the Get Details Method
        Admin existingAdmin = findById(id);

        if(existingAdmin.getStatus()==STATUS.ARCHIVED){
            throw new RuntimeException("Invalid : Admin already archived");
        }

        // Disabling Azure AD user account
        azureAdService.disableUserAccount(String.valueOf(existingAdmin.getObjectId()));


        // Archive Admin in Database
        Admin archivedAdmin = Admin.builder()
                .id(existingAdmin.getId())
                .objectId(existingAdmin.getObjectId())
                .firstName(existingAdmin.getFirstName())
                .lastName(existingAdmin.getLastName())
                .displayName(existingAdmin.getDisplayName())
                .mobile(existingAdmin.getMobile())
                .gender(existingAdmin.getGender())
                .userPrincipalName(existingAdmin.getUserPrincipalName())
                .contact_email(existingAdmin.getContact_email())
                .photoUrl(existingAdmin.getPhotoUrl())
                .isInitalLogged(existingAdmin.getIsInitalLogged())
                .verifyToken(existingAdmin.getVerifyToken())
                .tokenIssuedAt(existingAdmin.getTokenIssuedAt())
                .accountEnabled(false)
                .createdBy(existingAdmin.getCreatedBy())
                .createdAt(existingAdmin.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .userRole(existingAdmin.getUserRole())
                .profession(existingAdmin.getProfession())
                .department(existingAdmin.getDepartment())
                .build();

        adminRepository.save(archivedAdmin);
    }




    // Helper Methods for Azure Service
    private User mapAdminCreateRequestToUser(AdminCreateRequest request) {
        User newUser = new User();

        newUser.accountEnabled = true;
        newUser.displayName = request.getDisplayName();
        newUser.userPrincipalName = request.getUserPrincipalName();
        newUser.givenName = request.getFirstName();
        newUser.surname = request.getLastName();
        newUser.mobilePhone = request.getMobile();
        newUser.mailNickname = request.getUserPrincipalName().split("@")[0]; // Extract the mail nickname from the UPN

        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.password = request.getTempPassword();
        passwordProfile.forceChangePasswordNextSignIn = true;
        newUser.passwordProfile = passwordProfile;

        return newUser;
    }

    private User mapAdminUpdateRequestToUser(AdminUpdateRequest request) {
        User newUser = new User();

        newUser.displayName = request.getDisplayName();
        newUser.givenName = request.getFirstName();
        newUser.surname = request.getLastName();
        newUser.mobilePhone = request.getMobile();
        if(request.isWantToEnableAccount()){
            newUser.accountEnabled = true;
        }

        return newUser;
    }



}
