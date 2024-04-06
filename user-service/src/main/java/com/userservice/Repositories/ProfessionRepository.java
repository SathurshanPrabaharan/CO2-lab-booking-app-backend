package com.userservice.Repositories;


import com.userservice.Models.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessionRepository extends JpaRepository<Profession, UUID> {
}
