package com.userservice.Repositories;


import com.userservice.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {


    Optional<Admin> findByUserPrincipalName(String userPrincipalName);

    List<Admin> findAllByOrderByCreatedAtDesc();

    Optional<Admin> findByObjectId(UUID id);




}
