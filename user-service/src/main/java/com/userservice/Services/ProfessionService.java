package com.userservice.Services;


import com.userservice.DTO.Request.ProfessionCreateRequest;
import com.userservice.DTO.Request.ProfessionUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Models.Profession;

import java.util.List;
import java.util.UUID;

public interface ProfessionService {

    Profession saveProfession(ProfessionCreateRequest profession);
    List<Profession> getAllProfessions();

    List<Profession> getAllProfessions(String name, UUID createdBy, STATUS status);

    Profession updateProfession(UUID id, ProfessionUpdateRequest professionUpdateRequest);


    void archiveProfession(UUID id);
}
