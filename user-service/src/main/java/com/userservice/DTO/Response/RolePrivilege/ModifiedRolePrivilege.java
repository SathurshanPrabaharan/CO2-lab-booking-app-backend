package com.userservice.DTO.Response.RolePrivilege;

import com.userservice.Models.RolePrivilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedRolePrivilege {
    private UUID id;
    private String key;
    private String title;

    public ModifiedRolePrivilege(RolePrivilege rolePrivilege){
        this.id=rolePrivilege.getId();
        this.key=rolePrivilege.getKey();
        this.title= rolePrivilege.getTitle();
    }
}
