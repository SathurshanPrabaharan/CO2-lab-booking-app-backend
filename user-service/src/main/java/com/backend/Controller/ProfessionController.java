package com.backend.Controller;


import com.backend.DTOs.ProfessionSaveDTO;
import com.backend.Entities.Profession;
import com.backend.Exceptions.ProfessionNotFoundException;
import com.backend.ServicesImpls.ProfessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProfessionController {

    @Autowired
    private ProfessionServiceImpl professionServiceImpl;

    @PostMapping("/api/v1/users/professions")
    public String saveProfession(@RequestBody ProfessionSaveDTO professionSaveDTO ){

        return professionServiceImpl.addProfession(professionSaveDTO);

    }

    @GetMapping("/api/v1/users/professions")
    public List<Profession> getProfessions(){
        return professionServiceImpl.getProfessions();

    }






}
