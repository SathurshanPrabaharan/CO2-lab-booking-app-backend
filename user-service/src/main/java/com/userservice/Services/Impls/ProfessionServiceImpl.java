package com.userservice.Services.Impls;



import com.userservice.DTO.Request.ProfessionRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ProfessionNotFoundException;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Profession;
import com.userservice.Repositories.ProfessionRepository;
import com.userservice.Services.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
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
                .createdBy(professionRequest.getCreatedBy())
                .status(STATUS.valueOf(professionRequest.getStatus().toUpperCase()))
                .build();

        return  professionRepository.save(profession);
    }



    @Override
    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }


    @Override
    public List<Profession> getAllProfessions(String name, Long createdBy, STATUS status) {
        if (name == null && createdBy == null && status == null) {
            return professionRepository.findAll();
        }

        if (name != null && createdBy != null && status != null) {
            return professionRepository.findByNameAndCreatedByAndStatus(name, createdBy, status);
        } else if (name != null && createdBy != null) {
            return professionRepository.findByNameAndCreatedBy(name, createdBy);
        } else if (name != null && status != null) {
            return professionRepository.findByNameAndStatus(name, status);
        } else if (createdBy != null && status != null) {
            return professionRepository.findByCreatedByAndStatus(createdBy, status);
        } else if (name != null) {
            return professionRepository.findByName(name);
        } else if (createdBy != null) {
            return professionRepository.findByCreatedBy(createdBy);
        } else {
            return professionRepository.findByStatus(status);
        }
    }



    @Override
    public Profession updateProfession(UUID id,ProfessionRequest professionRequest) {


        Profession existingProfession = professionRepository.findById(id)
                .orElseThrow(
                    () -> new ProfessionNotFoundException("profession not found with id : " + id)
                );

        Profession updatedProfession = Profession.builder()
                .id(existingProfession.getId())
                .name(professionRequest.getName())
                .createdBy(professionRequest.getCreatedBy())
                .createdAt(existingProfession.getCreatedAt())
                .status(STATUS.valueOf(professionRequest.getStatus().toUpperCase()))
                .build();

        return  professionRepository.save(updatedProfession);

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
