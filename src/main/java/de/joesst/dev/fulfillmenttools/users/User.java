package de.joesst.dev.fulfillmenttools.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record User(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        Instant lastLogin,
        @JsonProperty("firstname") String firstName,
        @JsonProperty("lastname") String lastName,
        String username,
        String email,
        String status,
        String locale,
        List<AssignedRole> assignedRoles,
        List<String> facilityLimitations,
        Map<String, Object> authenticationProvider,
        Map<String, Object> customClaims,
        List<Map<String, Object>> assignedFacilities
) {}
