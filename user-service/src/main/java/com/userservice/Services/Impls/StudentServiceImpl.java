package com.userservice.Services.Impls;

import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.userservice.DTO.Request.Student.StudentArchiveRequest;
import com.userservice.DTO.Request.Student.StudentCreateRequest;
import com.userservice.DTO.Request.Student.StudentUpdateRequest;
import com.userservice.Enums.GENDER;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Student;
import com.userservice.Models.SupportModels.Course;
import com.userservice.Models.SupportModels.Department;
import com.userservice.Models.SupportModels.Profession;
import com.userservice.Models.UserRole;
import com.userservice.Repositories.StudentRepository;
import com.userservice.Repositories.SupportRepositories.CourseRepository;
import com.userservice.Repositories.SupportRepositories.DepartmentRepository;
import com.userservice.Repositories.SupportRepositories.ProfessionRepository;
import com.userservice.Repositories.UserRoleRepository;
import com.userservice.Services.AzureAdService;
import com.userservice.Services.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private AzureAdService azureAdService;
    private final StudentRepository studentRepository;
    private final UserRoleRepository userRoleRepository;
    private final ProfessionRepository professionRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              UserRoleRepository userRoleRepository,
                              ProfessionRepository professionRepository,
                              DepartmentRepository departmentRepository,
                              CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
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

    @Value("${azure.ad.domain-name}")
    private String domainName;


    @PostConstruct
    private void initializeAzureAdService() {
        this.azureAdService = new AzureAdService(clientId, clientSecret, tenantId);
    }

    @Override
    @Transactional
    public Student saveStudent(StudentCreateRequest request) {

        // Logic to set the userPrincipalName if it's not provided
        if (request.getRegNum() != null && !request.getRegNum().isEmpty() && (request.getUserPrincipalName() == null || request.getUserPrincipalName().isEmpty())) {
            String formattedRegNum = request.getRegNum().replace("/", "").toLowerCase();
            request.setUserPrincipalName(formattedRegNum + "@" + domainName);
        }

        Student existingStudent = studentRepository.findByUserPrincipalName(request.getUserPrincipalName()).orElse(null);

        if (existingStudent != null) {
            throw new IllegalArgumentException("'" + request.getUserPrincipalName() + "' already exists.");
        }



        // Create Azure AD user
        User newUser = mapStudentCreateRequestToUser(request);
        User createdAzureAdUser = azureAdService.createUser(newUser);

        if (createdAzureAdUser == null || createdAzureAdUser.id == null) {
            throw new RuntimeException("Failed to create Azure AD user!");
        }

        // Create Student in Database
        Student student = Student.builder()
                .id(UUID.randomUUID())
                .objectId(UUID.fromString(createdAzureAdUser.id))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .mobile(request.getMobile())
                .gender(GENDER.valueOf(request.getGender().toUpperCase()))
                .semester(request.getSemester())
                .regNum(request.getRegNum())
                .userPrincipalName(request.getUserPrincipalName())
                .photoUrl(request.getPhotoUrl())
                .createdBy(request.getCreatedBy())
                .accountEnabled(true)
                .status(STATUS.ACTIVE)
                .build();

        // Set UserRole
        UserRole userRole = userRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + request.getUserRoleId()));
        student.setUserRole(userRole);

        // Set Profession if exists
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id: " + request.getProfessionId()));
            student.setProfession(profession);
        }

        // Set Department if exists
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
            student.setDepartment(department);
        }

        // Set Responsible Course if exists
        if (request.getCurrentCourseIds() != null) {
            List<Course> responsibleCoursesList = courseRepository.findAllById(request.getCurrentCourseIds());
            // Converting the list to a set
            Set<Course> responsibleCoursesSet = new HashSet<>(responsibleCoursesList);
            student.setCurrentCourses(responsibleCoursesSet);
        }


        return studentRepository.save(student);
    }


    @Override
    public Student findById(UUID id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + id));
    }

    @Override
    public Student findByObjectId(UUID id) {
        Optional<Student> studentOptional = studentRepository.findByObjectId(id);
        return studentOptional.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + id));
    }



    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    @Override
    public Page<Student> filterStudent(UUID userRoleId, UUID professionId, UUID departmentId, UUID courseId, String gender, Short semester, String status, UUID createdBy, int page, int size) {
        // Fetch all students ordered by createdAt in descending order
        List<Student> allStudents = studentRepository.findAllByOrderByCreatedAtDesc();

        // Apply filters
        List<Student> filteredStudents = allStudents.stream()
                .filter(student -> userRoleId == null || (student.getUserRole() != null && student.getUserRole().getId().equals(userRoleId)))
                .filter(student -> professionId == null || (student.getProfession() != null && student.getProfession().getId().equals(professionId)))
                .filter(student -> departmentId == null || (student.getDepartment() != null && student.getDepartment().getId().equals(departmentId)))
                .filter(student -> gender == null || student.getGender().toString().equalsIgnoreCase(gender))
                .filter(student -> semester == null || student.getSemester().equals(semester))
                .filter(student -> createdBy == null || student.getCreatedBy().equals(createdBy))
                .filter(student -> status == null || student.getStatus().toString().equalsIgnoreCase(status))
                .filter(student -> courseId == null || student.getCurrentCourses().stream().anyMatch(course -> course.getId().equals(courseId)))
                .collect(Collectors.toList());

        // Apply pagination
        int start = Math.min(page * size, filteredStudents.size());
        int end = Math.min((page + 1) * size, filteredStudents.size());
        List<Student> paginatedList = filteredStudents.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), filteredStudents.size());
    }

    @Override
    public Student updateStudent(UUID id, StudentUpdateRequest request) {


        // Call the above Get Details Method
        Student existingStudent = findById(id);

        // Update Azure AD user
        User newUser = mapStudentUpdateRequestToUser(request);
        User createdAzureAdUser = azureAdService.updateUser(String.valueOf(existingStudent.getObjectId()),newUser);


        // Update Student in Database
        Student updatedStudent = Student.builder()
                .id(existingStudent.getId())
                .objectId(existingStudent.getObjectId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .displayName(request.getDisplayName())
                .mobile(request.getMobile())
                .gender(GENDER.valueOf(request.getGender().toUpperCase()))
                .semester(request.getSemester())
                .regNum(existingStudent.getRegNum())
                .userPrincipalName(existingStudent.getUserPrincipalName())
                .contact_email(request.getContact_email())
                .photoUrl(request.getPhotoUrl())
                .isInitalLogged(request.getIsInitalLogged())
                .verifyToken(request.getVerifyToken())
                .tokenIssuedAt(request.getTokenIssuedAt())
                .accountEnabled(existingStudent.getAccountEnabled())
                .createdBy(existingStudent.getCreatedBy())
                .createdAt(existingStudent.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.valueOf(request.getStatus().toUpperCase()))
                .build();

        // Set UserRole
        UserRole userRole = userRoleRepository.findById(request.getUserRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id: " + request.getUserRoleId()));
        updatedStudent.setUserRole(userRole);

        // Set Profession if exists
        if (request.getProfessionId() != null) {
            Profession profession = professionRepository.findById(request.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id: " + request.getProfessionId()));
            updatedStudent.setProfession(profession);
        }

        // Set Department if exists
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
            updatedStudent.setDepartment(department);
        }

        // Set Responsible Course if exists
        if (request.getCurrentCourseIds() != null) {
            List<Course> responsibleCoursesList = courseRepository.findAllById(request.getCurrentCourseIds());
            // Converting the list to a set
            Set<Course> responsibleCoursesSet = new HashSet<>(responsibleCoursesList);
            updatedStudent.setCurrentCourses(responsibleCoursesSet);
        }

        // Enable the Account in DB
        if(request.isWantToEnableAccount()){
            updatedStudent.setAccountEnabled(true);
        }

        return  studentRepository.save(updatedStudent);

    }

    @Override
    public void archiveStudent(UUID id, StudentArchiveRequest request) {

        // Call the Get Details Method
        Student existingStudent = findById(id);

        if(existingStudent.getStatus()==STATUS.ARCHIVED){
            throw new RuntimeException("Invalid : Student already archived");
        }

        // Disabling Azure AD user account
        azureAdService.disableUserAccount(String.valueOf(existingStudent.getObjectId()));


        // Archive Student in Database
        Student archivedStudent = Student.builder()
                .id(existingStudent.getId())
                .objectId(existingStudent.getObjectId())
                .firstName(existingStudent.getFirstName())
                .lastName(existingStudent.getLastName())
                .displayName(existingStudent.getDisplayName())
                .mobile(existingStudent.getMobile())
                .gender(existingStudent.getGender())
                .semester(existingStudent.getSemester())
                .regNum(existingStudent.getRegNum())
                .userPrincipalName(existingStudent.getUserPrincipalName())
                .contact_email(existingStudent.getContact_email())
                .photoUrl(existingStudent.getPhotoUrl())
                .isInitalLogged(existingStudent.getIsInitalLogged())
                .verifyToken(existingStudent.getVerifyToken())
                .tokenIssuedAt(existingStudent.getTokenIssuedAt())
                .accountEnabled(false)
                .createdBy(existingStudent.getCreatedBy())
                .createdAt(existingStudent.getCreatedAt())
                .updatedBy(request.getUpdatedBy())
                .status(STATUS.ARCHIVED)
                .userRole(existingStudent.getUserRole())
                .profession(existingStudent.getProfession())
                .department(existingStudent.getDepartment())
                .currentCourses(existingStudent.getCurrentCourses())
                .build();

        studentRepository.save(archivedStudent);
    }




    // Helper Methods for Azure Service
    private User mapStudentCreateRequestToUser(StudentCreateRequest request) {
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

    private User mapStudentUpdateRequestToUser(StudentUpdateRequest request) {
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
