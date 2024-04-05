package com.userservice.Controllers;

import com.userservice.Models.Profession;
import com.userservice.Services.ProfessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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



}
