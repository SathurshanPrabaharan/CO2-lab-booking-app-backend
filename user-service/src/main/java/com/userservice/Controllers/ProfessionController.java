package com.userservice.Controllers;

import com.userservice.DTO.Request.ProfessionRequest;
import com.userservice.DTO.Response.ProfessionListResponse;
import com.userservice.DTO.Response.ProfessionResponse;
import com.userservice.DTO.Response.ProfessionResponseMessage;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ProfessionNotFoundException;
import com.userservice.Models.Profession;
import com.userservice.Repositories.ProfessionRepository;
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

    @Autowired
    private ProfessionRepository professionRepository;

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
    public ResponseEntity<Object> getAllProfessions(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long createdBy,
            @RequestParam(required = false) String status
    ){
        List<Profession> foundedProfessions;

        if (name == null && createdBy == null && status == null){
           foundedProfessions = professionService.getAllProfessions();
        }else if(status == null){

            foundedProfessions = professionService.getAllProfessions(name, createdBy, null);
        }else{
            foundedProfessions = professionService.getAllProfessions(name, createdBy, STATUS.valueOf(status));
        }


        String message = "Professions fetched successfully";

        ProfessionListResponse response;
        if(page==null && size==null) {
            response = new ProfessionListResponse(message, foundedProfessions);
        }else{
            response = new ProfessionListResponse(message, foundedProfessions, page, size);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> getProfessionDetails(@PathVariable UUID id) throws ProfessionNotFoundException {
        Profession profession = professionRepository.findById(id)
                .orElseThrow(() -> new ProfessionNotFoundException("profession not found with id : " + id));
        String message = "profession details fetched successfully";
        ProfessionResponse response = new ProfessionResponse(message, profession);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateProfession(@PathVariable UUID id ,@RequestBody ProfessionRequest professionRequest) throws ProfessionNotFoundException {
        Profession updatedProfession  = professionService.updateProfession(id,professionRequest);
        String message = "Profession updated successfully";
        ProfessionResponse response = new ProfessionResponse(message,updatedProfession);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveProfession(@PathVariable UUID id) throws ProfessionNotFoundException {
        professionService.archiveProfession(id);
        String message = "Profession archived successfully";
        ProfessionResponseMessage response = new ProfessionResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
