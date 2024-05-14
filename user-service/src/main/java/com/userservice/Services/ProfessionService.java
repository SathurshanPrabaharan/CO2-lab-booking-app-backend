package com.userservice.Services;


import com.userservice.DTO.Request.ProfessionRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Profession;

import java.util.List;
import java.util.UUID;

public interface ProfessionService {

    Profession saveProfession(ProfessionRequest profession);
    List<Profession> getAllProfessions();


    List<Profession> getAllProfessions(String name, Long createdBy, STATUS status);


    Profession getProfessionById(UUID id);
    Profession updateProfession(Profession profession, UUID id);
    void deleteProfession(UUID id);
}
