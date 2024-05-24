package com.userservice.DTO.Request.UserRole;

import com.userservice.Enums.STATUS;
import com.userservice.Models.RolePrivilege;
import com.userservice.Validations.ValidStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRolePatchRequest {

    @NotNull(message = "Invalid updatedBy: updatedBy cannot be null")
    private UUID updatedBy;

    //can only pass either privileges IDs or privileges
    private List<UUID> privilegeIDs;
    private List<RolePrivilege> privilegeList;

    // Custom validation method
    private void validatePrivileges() {
        if (privilegeIDs != null && !privilegeIDs.isEmpty() && privilegeList != null && !privilegeList.isEmpty()) {
            throw new IllegalArgumentException("Cannot provide both privilegeIDs and privilegeList at sametime.");
        }
    }

    // Helper method to determine if privilege IDs are provided
    public boolean hasPrivilegeIDs() {
        return privilegeIDs != null && !privilegeIDs.isEmpty();
    }

    // Helper method to determine if privilege objects are provided
    public boolean hasPrivilegeList() {
        return privilegeList != null && !privilegeList.isEmpty();
    }

    // Method to add createdBy and Status to each privilege in the privilegeList
    public void addCreatedByAndStatusToPrivileges() {
        if (privilegeList != null) {
            for (RolePrivilege privilege : privilegeList) {
                privilege.setCreatedBy(updatedBy);
                privilege.setStatus(STATUS.ACTIVE);
            }
        }
    }


}
