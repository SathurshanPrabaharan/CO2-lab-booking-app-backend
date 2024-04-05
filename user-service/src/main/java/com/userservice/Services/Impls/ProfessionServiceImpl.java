package com.userservice.Services.Impls;



import com.userservice.Exception.ResourceNotFoundException;
import com.userservice.Models.Profession;
import com.userservice.Repositories.ProfessionRepository;
import com.userservice.Services.ProfessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessionServiceImpl implements ProfessionService {


    private ProfessionRepository professionRepository;

    public ProfessionServiceImpl(ProfessionRepository professionRepository) {
        super();
        this.professionRepository = professionRepository;
    }

    @Override
    public Profession saveProfession(Profession profession) {
        return professionRepository.save(profession);
    }

    @Override
    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }

    @Override
    public Profession getProfessionById(UUID id) {

        return professionRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "Id", id)
                );

    }


    @Override
    public Profession updateProfession(Profession profession, UUID id) {


        Profession existingProfession = professionRepository.findById(id)
                .orElseThrow(
                    () -> new ResourceNotFoundException("Employee", "Id", id)
                );

        existingProfession.setName(profession.getName());
        existingProfession.setStatus(profession.getStatus());

        professionRepository.save(existingProfession);
        return existingProfession;
    }

}
