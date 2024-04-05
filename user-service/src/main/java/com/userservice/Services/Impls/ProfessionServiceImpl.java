package com.userservice.Services.Impls;



import com.userservice.Models.Profession;
import com.userservice.Repositories.ProfessionRepository;
import com.userservice.Services.ProfessionService;
import org.springframework.stereotype.Service;

import java.util.List;

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



}
