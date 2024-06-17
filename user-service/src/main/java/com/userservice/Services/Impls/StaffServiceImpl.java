package com.userservice.Services.Impls;

import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.userservice.DTO.Request.Staff.StaffArchiveRequest;
import com.userservice.DTO.Request.Staff.StaffCreateRequest;
import com.userservice.DTO.Request.Staff.StaffUpdateRequest;
import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Staff;
import com.userservice.Models.SupportModels.Course;
import com.userservice.Models.SupportModels.Department;
import com.userservice.Models.SupportModels.Profession;
import com.userservice.Models.UserRole;
import com.userservice.Repositories.StaffRepository;
import com.userservice.Repositories.SupportRepositories.CourseRepository;
import com.userservice.Repositories.SupportRepositories.DepartmentRepository;
import com.userservice.Repositories.SupportRepositories.ProfessionRepository;
import com.userservice.Repositories.UserRoleRepository;
import com.userservice.Services.AzureAdService;
import com.userservice.Services.StaffService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {

    private  AzureAdService azureAdService;
    private final StaffRepository staffRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProfessionRepository professionRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository,
                            UserRoleRepository userRoleRepository,
                            ProfessionRepository professionRepository,
                            DepartmentRepository departmentRepository,
                            CourseRepository courseRepository) {
        this.staffRepository = staffRepository;
        this.userRoleRepository = userRoleRepository;
        this.professionRepository = professionRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
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
    public Staff saveStaff(StaffCreateRequest request) {
        Staff existingStaff = staffRepository.findByUserPrincipalName(request.getUserPrincipalName()).orElse(null);

        if (existingStaff != null) {
            throw new IllegalArgumentException("'" + request.getUserPrincipalName() + "' already exists.");
        }

        // Create Azure AD user
        User newUser = mapStaffCreateRequestToUser(request);
        User createdAzureAdUser = azureAdService.createUser(newUser);

        if (createdAzureAdUser == null || createdAzureAdUser.id == null) {
            throw new RuntimeException("Failed to create Azure AD user!");
        }

        // Create Staff in Database
        Staff staff = Staff.builder()
                .id(UUID.randomUUID())
                .objectId(UUID.fromString(createdAzureAdUser.id))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .mobile(request.getMobile())
                .gender(GENDER.valueOf(request.getGender().toUpperCase()))
                .userPrincipalName(request.getUserPrincipalName())
                .photoUrl(request.getPhotoUrl())
                .createdBy(request.getCreatedBy())
                .accountEnabled(true)
                .status(STATUS.ACTIVE)
                .build();

        // Set UserRole
        UserRole userRole = userRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + request.getUserRoleId()));
        staff.setUserRole(userRole);

        // Set Profession if exists
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id: " + request.getProfessionId()));
            staff.setProfession(profession);
        }

        // Set Department if exists
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
            staff.setDepartment(department);
        }

        // Set Responsible Course if exists
        if (request.getResponsibleCourseIds() != null) {
            List<Course> responsibleCoursesList = courseRepository.findAllById(request.getResponsibleCourseIds());
            // Converting the list to a set
            Set<Course> responsibleCoursesSet = new HashSet<>(responsibleCoursesList);
            staff.setResponsibleCourses(responsibleCoursesSet);
        }


        return staffRepository.save(staff);
    }


    @Override
    public Staff findById(UUID id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        return staffOptional.orElseThrow(() -> new ResourceNotFoundException("Staff not found with id : " + id));
    }



    @Override
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }


    @Override
    public Page<Staff> filterStaff(UUID userRoleId, UUID professionId, UUID departmentId, String gender, UUID createdBy, String status, int page, int size) {
        // Fetch all staff ordered by createdAt in descending order
        List<Staff> allStaff = staffRepository.findAllByOrderByCreatedAtDesc();

        // Apply filters
        List<Staff> filteredStaff = allStaff.stream()
                .filter(staff -> userRoleId == null || (staff.getUserRole() != null && staff.getUserRole().getId().equals(userRoleId)))
                .filter(staff -> professionId == null || (staff.getProfession() != null && staff.getProfession().getId().equals(professionId)))
                .filter(staff -> departmentId == null || (staff.getDepartment() != null && staff.getDepartment().getId().equals(departmentId)))
                .filter(staff -> gender == null || staff.getGender().toString().equalsIgnoreCase(gender))
                .filter(staff -> createdBy == null || staff.getCreatedBy().equals(createdBy))
                .filter(staff -> status == null || staff.getStatus().toString().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        // Apply pagination
        int start = Math.min(page * size, filteredStaff.size());
        int end = Math.min((page + 1) * size, filteredStaff.size());
        List<Staff> paginatedList = filteredStaff.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), filteredStaff.size());
    }


    @Override
    public Staff updateStaff(UUID id, StaffUpdateRequest request) {


        // Call the above Get Details Method
        Staff existingStaff = findById(id);

        // Update Azure AD user
        User newUser = mapStaffUpdateRequestToUser(request);
        User createdAzureAdUser = azureAdService.updateUser(String.valueOf(existingStaff.getObjectId()),newUser);


        // Update Staff in Database
        Staff updatedStaff = Staff.builder()
                .id(existingStaff.getId())
                .objectId(existingStaff.getObjectId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .mobile(request.getMobile())
                .gender(GENDER.valueOf(request.getGender().toUpperCase()))
                .userPrincipalName(existingStaff.getUserPrincipalName())
                .photoUrl(request.getPhotoUrl())
                .contact_email(request.getContact_email())
                .isInitalLogged(request.getIsInitalLogged())
                .verifyToken(request.getVerifyToken())
                .tokenIssuedAt(request.getTokenIssuedAt())
                .accountEnabled(existingStaff.getAccountEnabled())
                .createdBy(existingStaff.getCreatedBy())
                .createdAt(existingStaff.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.valueOf(request.getStatus().toUpperCase()))
                .build();

        // Set UserRole
        UserRole userRole = userRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + request.getUserRoleId()));
        updatedStaff.setUserRole(userRole);

        // Set Profession if exists
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id: " + request.getProfessionId()));
            updatedStaff.setProfession(profession);
        }

        // Set Department if exists
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
            updatedStaff.setDepartment(department);
        }

        // Set Responsible Course if exists
        if (request.getResponsibleCourseIds() != null) {
            List<Course> responsibleCoursesList = courseRepository.findAllById(request.getResponsibleCourseIds());
            // Converting the list to a set
            Set<Course> responsibleCoursesSet = new HashSet<>(responsibleCoursesList);
            updatedStaff.setResponsibleCourses(responsibleCoursesSet);
        }

        // Enable the Account
        if(request.isWantToEnableAccount()){
            updatedStaff.setAccountEnabled(true);
        }

        return  staffRepository.save(updatedStaff);

    }

    @Override
    public void archiveStaff(UUID id, StaffArchiveRequest request) {

        // Call the Get Details Method
        Staff existingStaff = findById(id);

        if(existingStaff.getStatus()==STATUS.ARCHIVED){
            throw new RuntimeException("Invalid : Staff already archived");
        }

        // Disabling Azure AD user account
        azureAdService.disableUserAccount(String.valueOf(existingStaff.getObjectId()));


        // Archive Staff in Database
        Staff archivedStaff = Staff.builder()
                .id(existingStaff.getId())
                .objectId(existingStaff.getObjectId())
                .firstName(existingStaff.getFirstName())
                .lastName(existingStaff.getLastName())
                .displayName(existingStaff.getDisplayName())
                .mobile(existingStaff.getMobile())
                .gender(existingStaff.getGender())
                .userPrincipalName(existingStaff.getUserPrincipalName())
                .photoUrl(existingStaff.getPhotoUrl())
                .contact_email(existingStaff.getContact_email())
                .isInitalLogged(existingStaff.getIsInitalLogged())
                .verifyToken(existingStaff.getVerifyToken())
                .tokenIssuedAt(existingStaff.getTokenIssuedAt())
                .accountEnabled(false)
                .createdBy(existingStaff.getCreatedBy())
                .createdAt(existingStaff.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .userRole(existingStaff.getUserRole())
                .profession(existingStaff.getProfession())
                .department(existingStaff.getDepartment())
                .responsibleCourses(existingStaff.getResponsibleCourses())
                .build();

        staffRepository.save(archivedStaff);
    }




    // Helper Methods for Azure Service
    private User mapStaffCreateRequestToUser(StaffCreateRequest request) {
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

    private User mapStaffUpdateRequestToUser(StaffUpdateRequest request) {
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
