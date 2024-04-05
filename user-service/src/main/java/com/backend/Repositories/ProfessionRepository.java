package com.backend.Repositories;

import com.backend.Entities.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessionRepository extends JpaRepository<Profession,Long> {

}
