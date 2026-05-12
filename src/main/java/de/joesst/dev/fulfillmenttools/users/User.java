package de.joesst.dev.fulfillmenttools.users;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

/**
 * Represents a user in fulfillmenttools.
 *
 * <p>The API JSON uses {@code firstname} and {@code lastname} (lowercase 'n');
 * {@code @JsonProperty} maps these to the idiomatic Java names {@code firstName}
 * and {@code lastName}.
 */
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
        List<AssignedRole> assignedRoles,
        List<String> facilityLimitations
) {}
