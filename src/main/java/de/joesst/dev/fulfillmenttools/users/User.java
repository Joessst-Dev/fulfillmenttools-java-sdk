package de.joesst.dev.fulfillmenttools.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.joesst.dev.fulfillmenttools.id.UserId;

import java.time.Instant;
import java.util.List;

/**
 * A fulfillmenttools platform user.
 *
 * @param id                     the unique user identifier
 * @param version                the optimistic-locking version number
 * @param created                the timestamp when the user was created
 * @param lastModified           the timestamp of the most recent modification
 * @param lastLogin              the timestamp of the user's most recent login
 * @param firstName              the user's given name
 * @param lastName               the user's family name
 * @param username               the login username
 * @param email                  the user's email address
 * @param status                 the user account status (e.g. {@code ACTIVE})
 * @param locale                 the user's preferred locale (e.g. {@code en_US})
 * @param assignedRoles          the roles assigned to the user
 * @param facilityLimitations    facility IDs the user is limited to
 * @param authenticationProvider the authentication provider configuration for this user
 * @param customClaims           deprecated custom claims; prefer {@link #assignedRoles()}
 * @param assignedFacilities     deprecated directly-assigned facilities;
 *                               prefer {@link #assignedRoles()} with context limitations
 */
public record User(
        UserId id,
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
        AuthenticationProvider authenticationProvider,
        @Deprecated CustomClaims customClaims,
        @Deprecated List<UserAssignedFacility> assignedFacilities
) {}
