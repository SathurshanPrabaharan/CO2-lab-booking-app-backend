package com.configurationservice.DTO.Response.SupportModelResponses;


import com.configurationservice.Enums.GENDER;
import com.configurationservice.Enums.STATUS;
import com.configurationservice.Models.SupportModels.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedStaffSimple {

    private UUID id;
    private String firstName;
    private String lastName;
    private String profession;


    public ModifiedStaffSimple(Staff staff) {
        this.id = staff.getId();
        this.firstName = staff.getFirstName();
        this.lastName = staff.getLastName();


        if (staff.getProfession() != null) {
            this.profession = staff.getProfession().getName();
        }


    }

}
