package com.bookingservice.DTO.Response.SupportModelResponses;



import com.bookingservice.Models.SupportModels.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedAdminSimple {

    private UUID id;
    private String firstName;
    private String lastName;

    public ModifiedAdminSimple(Admin admin) {
        this.id = admin.getId();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();



    }

}
