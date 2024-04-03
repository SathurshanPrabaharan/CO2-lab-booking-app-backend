package com.backend.Services;

import com.backend.DTOs.ProfessionSaveDTO;
import com.backend.Entities.Profession;

import java.util.List;
import java.util.Optional;

public interface ProfessionService {
    String addProfession(ProfessionSaveDTO professionSaveDTO);

    public List<Profession> getProfessions();

   public Optional<Profession> getProfession(long id);
}
