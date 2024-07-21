package com.userservice.Repositories;


import com.userservice.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {


    Optional<Student> findByUserPrincipalName(String userPrincipalName);

    List<Student> findAllByOrderByCreatedAtDesc();

    Optional<Student> findByObjectId(UUID id);


}
