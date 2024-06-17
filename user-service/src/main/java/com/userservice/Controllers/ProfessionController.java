package com.userservice.Controllers;

import com.userservice.DTO.Request.Profession.ProfessionCreateRequest;
import com.userservice.DTO.Request.Profession.ProfessionUpdateRequest;
import com.userservice.DTO.Response.Profession.ProfessionListResponse;
import com.userservice.DTO.Response.Profession.ProfessionResponse;
import com.userservice.DTO.Response.ResponseMessage;
import com.userservice.Enums.STATUS;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Models.SupportModels.Profession;
import com.userservice.Services.ProfessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/professions")
@RequiredArgsConstructor
public class ProfessionController {

    private final ProfessionService professionService;

    @PostMapping
    public ResponseEntity<Object> saveEmployee(@RequestBody @Valid ProfessionCreateRequest profession){
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
            @RequestParam(required = false) UUID createdBy,
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
    public ResponseEntity<Object> getProfessionDetails(@PathVariable UUID id) throws ResourceNotFoundException {
        Profession profession = professionService.findById(id);
        String message = "Profession details fetched successfully";
        ProfessionResponse response = new ProfessionResponse(message, profession);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> updateProfession(@PathVariable UUID id ,@RequestBody @Valid ProfessionUpdateRequest professionUpdateRequest) throws ResourceNotFoundException {
        Profession updatedProfession  = professionService.updateProfession(id,professionUpdateRequest);
        String message = "Profession updated successfully";
        ProfessionResponse response = new ProfessionResponse(message,updatedProfession);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> archiveProfession(@PathVariable UUID id) throws ResourceNotFoundException {
        professionService.archiveProfession(id);
        String message = "Profession archived successfully";
        ResponseMessage response = new ResponseMessage(message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
