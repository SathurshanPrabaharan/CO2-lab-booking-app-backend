package com.userservice.DTO.Response.UserRole;

import com.userservice.DTO.Response.RolePrivilege.ModifiedRolePrivilege;
import com.userservice.Models.RolePrivilege;
import com.userservice.Models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedUserRole {

    private UUID id;
    private String key;
    private String title;
    private Set<ModifiedRolePrivilege> privilegeSet;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private UUID createdBy;
//    private UUID updatedBy;

    public ModifiedUserRole(UserRole userRole ){
        this.id=userRole.getId();
        this.key= userRole.getKey();
        this.title= userRole.getKey();
//        this.createdAt=userRole.getCreatedAt();
//        this.updatedAt=userRole.getUpdatedAt();
//        this.createdBy=userRole.getCreatedBy();
//        this.updatedBy=userRole.getUpdatedBy();

        Set<ModifiedRolePrivilege> modifiedRolePrivilegeSet = new HashSet<>();
        for(RolePrivilege rolePrivilege: userRole.getPrivilegeSet()){
            //modify the rolePrivilege
            ModifiedRolePrivilege temp = new ModifiedRolePrivilege(rolePrivilege);
            //Add to set
            modifiedRolePrivilegeSet.add(temp);
        }
        //Add the result to final result
        this.privilegeSet=modifiedRolePrivilegeSet;

    }

}
