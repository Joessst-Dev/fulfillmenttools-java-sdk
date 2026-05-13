package de.joesst.dev.fulfillmenttools.internal.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.joesst.dev.fulfillmenttools.users.AssignedRole;

import java.util.List;

record UpdateUserBody(Integer version, List<ModifyUserAction> actions) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record ModifyUserAction(
            String action,
            @JsonProperty("firstname") String firstName,
            @JsonProperty("lastname") String lastName,
            String email,
            String password,
            String locale,
            List<AssignedRole> assignedRoles
    ) {
        ModifyUserAction(String firstName, String lastName, String email, String password,
                         String locale, List<AssignedRole> assignedRoles) {
            this("ModifyUser", firstName, lastName, email, password, locale, assignedRoles);
        }
    }
}
