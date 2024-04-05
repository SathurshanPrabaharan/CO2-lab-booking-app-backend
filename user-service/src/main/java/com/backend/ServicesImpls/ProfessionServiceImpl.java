package com.backend.ServicesImpls;

import com.backend.DTOs.ProfessionSaveDTO;
import com.backend.Entities.Profession;
import com.backend.Repositories.ProfessionRepository;
import com.backend.Services.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    private  final ProfessionRepository professionRepository;

    @Autowired
    public ProfessionServiceImpl(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    @Override
    public String addProfession(ProfessionSaveDTO professionSaveDTO) {
        Profession profession = new Profession(professionSaveDTO.getName());
        professionRepository.save(profession);
        return "Profession successfully created";
    }

    @Override
    public List<Profession> getProfessions() {
        return professionRepository.findAll();
    }


    @Override
    public Optional<Profession> getProfession(long id) {
        return professionRepository.findById(id);
    }
}
