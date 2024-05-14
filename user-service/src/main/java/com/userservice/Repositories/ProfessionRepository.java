package com.userservice.Repositories;

import com.userservice.Enums.STATUS;
import com.userservice.Models.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfessionRepository extends JpaRepository<Profession, UUID> {
    List<Profession> findByNameAndCreatedByAndStatus(String name, Long createdBy, STATUS status);

    List<Profession> findByNameAndCreatedBy(String name, Long createdBy);

    List<Profession> findByNameAndStatus(String name, STATUS status);

    List<Profession> findByCreatedByAndStatus(Long createdBy, STATUS status);

    List<Profession> findByName(String name);

    List<Profession> findByCreatedBy(Long createdBy);

    List<Profession> findByStatus(STATUS status);
}
