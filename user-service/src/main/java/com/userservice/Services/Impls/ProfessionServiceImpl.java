package com.userservice.Services.Impls;



import com.userservice.DTO.Request.ProfessionRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
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
    public Profession saveProfession( ProfessionRequest professionRequest) {
        Profession profession = Profession.builder()
                .id(UUID.randomUUID())
                .name(professionRequest.getName())
                .created_by(professionRequest.getCreated_by())
                .status(STATUS.valueOf(professionRequest.getStatus().toUpperCase()))
                .build();

        return  professionRepository.save(profession);
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


    @Override
    public void deleteProfession(UUID id) {

        professionRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "Id", id)
                );
        professionRepository.deleteById(id);
    }

}
