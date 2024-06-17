package com.userservice.Services;


import com.userservice.DTO.Request.Staff.StaffArchiveRequest;
import com.userservice.DTO.Request.Staff.StaffCreateRequest;
import com.userservice.DTO.Request.Staff.StaffUpdateRequest;
import com.userservice.Models.Staff;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface StaffService {

    Staff saveStaff(StaffCreateRequest staff);

    List<Staff> getAllStaffs();

    Page<Staff> filterStaff(UUID userRoleId, UUID professionId, UUID departmentId, String gender, UUID createdBy, String status, int page, int size);

    Staff findById(UUID id);

    Staff updateStaff(UUID id, StaffUpdateRequest request);


    void archiveStaff(UUID id, StaffArchiveRequest request);

}
