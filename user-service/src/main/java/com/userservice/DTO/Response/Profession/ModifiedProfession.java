package com.userservice.DTO.Response.Profession;


import com.userservice.Models.SupportModels.Profession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedProfession {
    private UUID id;
    private String name;

    public ModifiedProfession(Profession profession){
        this.id=profession.getId();
        this.name=profession.getName();
    }
}
