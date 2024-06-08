package com.bookingservice.DTO.Response.SupportModelResponses;


import com.bookingservice.Models.SupportModels.Staff;
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
