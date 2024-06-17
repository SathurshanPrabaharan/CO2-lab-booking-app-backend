package com.userservice.Repositories.SupportRepositories;

import com.userservice.Enums.STATUS;
import com.userservice.Models.SupportModels.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, UUID> {
    List<Profession> findByNameAndCreatedByAndStatus(String name, UUID createdBy, STATUS status);

    List<Profession> findByNameAndCreatedBy(String name, UUID createdBy);

    List<Profession> findByNameAndStatus(String name, STATUS status);

    List<Profession> findByCreatedByAndStatus(UUID createdBy, STATUS status);

    List<Profession> findByName(String name);

    List<Profession> findByCreatedBy(UUID createdBy);

    List<Profession> findByStatus(STATUS status);
}
