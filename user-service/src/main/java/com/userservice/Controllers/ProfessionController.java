package com.userservice.Controllers;

import com.userservice.DTO.Request.ProfessionRequest;
import com.userservice.DTO.Response.ProfessionResponse;
import com.userservice.Models.Profession;
import com.userservice.Services.ProfessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/professions")
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        super();
        this.professionService = professionService;
    }

    @PostMapping
    public ResponseEntity<Object> saveEmployee(@RequestBody @Valid ProfessionRequest profession){
        Profession savedProfession = professionService.saveProfession(profession);
        String message = "Profession created successfully";
        ProfessionResponse response = new ProfessionResponse(message, savedProfession);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @GetMapping
    public List<Profession> getAllProfessions(){
        return professionService.getAllProfessions();
    }


    @GetMapping("{id}")
    public ResponseEntity<Profession> getEmployeeById(@PathVariable("id") UUID professionId){
        return new ResponseEntity<Profession>(professionService.getProfessionById(professionId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Profession> updateProfession(@PathVariable("id") UUID id
            ,@RequestBody Profession profession){
        return new ResponseEntity<Profession>(professionService.updateProfession(profession, id), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProfession(@PathVariable("id") UUID id){

        professionService.deleteProfession(id);

        return new ResponseEntity<String>("Profession deleted successfully!.", HttpStatus.OK);
    }



}
