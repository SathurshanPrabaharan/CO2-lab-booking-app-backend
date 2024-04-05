package com.userservice.Controllers;

import com.userservice.Models.Profession;
import com.userservice.Services.ProfessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProfessionController {

    private ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        super();
        this.professionService = professionService;
    }

    @PostMapping("/api/v1/users/professions")
    public ResponseEntity<Profession> saveEmployee(@RequestBody Profession profession){
        return new ResponseEntity<Profession>(professionService.saveProfession(profession), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/users/professions")
    public List<Profession> getAllProfessions(){
        return professionService.getAllProfessions();
    }


    @GetMapping("/api/v1/users/professions/{id}")
    public ResponseEntity<Profession> getEmployeeById(@PathVariable("id") UUID professionId){
        return new ResponseEntity<Profession>(professionService.getProfessionById(professionId), HttpStatus.OK);
    }



}
