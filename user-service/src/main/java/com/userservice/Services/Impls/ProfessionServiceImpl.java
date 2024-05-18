package com.userservice.Services.Impls;



import com.userservice.DTO.Request.Profession.ProfessionCreateRequest;
import com.userservice.DTO.Request.Profession.ProfessionUpdateRequest;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.Profession;
import com.userservice.Repositories.ProfessionRepository;
import com.userservice.Services.ProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessionServiceImpl implements ProfessionService {

    private final ProfessionRepository professionRepository;


    @Override
    public Profession saveProfession( ProfessionCreateRequest professionRequest) {
        Profession profession = Profession.builder()
                .id(UUID.randomUUID())
                .name(professionRequest.getName())
                .createdBy(professionRequest.getCreatedBy())
                .status(STATUS.ACTIVE)
                .build();

        return  professionRepository.save(profession);
    }

    @Override
    public Profession findById(UUID id) {
        Optional<Profession> professionOptional = professionRepository.findById(id);
        return professionOptional.orElseThrow(() -> new ResourceNotFoundException("Profession not found with id : " + id));
    }


    @Override
    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }


    @Override
    public List<Profession> getAllProfessions(String name, UUID createdBy, STATUS status) {
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
    public Profession updateProfession(UUID id, ProfessionUpdateRequest professionUpdateRequest) {


        Profession existingProfession = professionRepository.findById(id)
                .orElseThrow(
                    () -> new ResourceNotFoundException("profession not found with id : " + id)
                );

        Profession updatedProfession = Profession.builder()
                .id(existingProfession.getId())
                .name(professionUpdateRequest.getName())
                .createdBy(existingProfession.getCreatedBy())
                .createdAt(existingProfession.getCreatedAt())
                .updatedBy(professionUpdateRequest.getUpdatedBy())
                .status(STATUS.valueOf(professionUpdateRequest.getStatus().toUpperCase()))
                .build();

        return  professionRepository.save(updatedProfession);

    }


    @Override
    public void archiveProfession(UUID id) {

        Profession existingProfession = professionRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("profession not found with id : " + id)
                );
        if(existingProfession.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Profession already archived");
        }

        Profession archivedProfession = Profession.builder()
                .id(existingProfession.getId())
                .name(existingProfession.getName())
                .createdBy(existingProfession.getCreatedBy())
                .createdAt(existingProfession.getCreatedAt())
                .status(STATUS.ARCHIVED)
                .build();

        professionRepository.save(archivedProfession);
    }

}
