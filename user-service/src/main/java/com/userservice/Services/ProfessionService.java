package com.userservice.Services;


import com.userservice.Models.Profession;

import java.util.List;

public interface ProfessionService {

    Profession saveProfession(Profession profession);
    List<Profession> getAllProfessions();
//    Profession getProfessionById(UUID id);
//    Profession updateProfession(Profession inventory, UUID id);
//    void deleteProfession(long id);
}
