package de.joesst.dev.fulfillmenttools.internal.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

record CreateUserBody(
        String username,
        String password,
        @JsonProperty("firstname") String firstName,
        @JsonProperty("lastname") String lastName,
        String email,
        String locale,
        List<Map<String, Object>> assignedRoles
) {}
