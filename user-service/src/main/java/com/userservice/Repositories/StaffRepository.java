package com.userservice.Repositories;

import com.userservice.Enums.STATUS;
import com.userservice.Models.Staff;
import com.userservice.Models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {


    Optional<Staff> findByUserPrincipalName(String userPrincipalName);

    List<Staff> findAllByOrderByCreatedAtDesc();

    Optional<Staff> findByObjectId(UUID id);


}
