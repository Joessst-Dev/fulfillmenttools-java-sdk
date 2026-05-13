package de.joesst.dev.fulfillmenttools.internal.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.joesst.dev.fulfillmenttools.users.AssignedRole;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateUserBody(
        String username,
        String password,
        @JsonProperty("firstname") String firstName,
        @JsonProperty("lastname") String lastName,
        String email,
        String locale,
        List<AssignedRole> assignedRoles
) {}
