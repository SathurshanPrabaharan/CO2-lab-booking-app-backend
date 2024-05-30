package com.inventoryservice.Repositories;

import com.inventoryservice.Models.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface SoftwareRepository extends JpaRepository<Software,UUID> {

    Optional<Software> findById(UUID id);

    List<Software> findByName(String name);

    List<Software> findByVersion(String version);
}
