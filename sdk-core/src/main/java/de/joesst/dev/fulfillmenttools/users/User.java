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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private UserId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private Instant lastLogin;
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String status;
        private String locale;
        private List<AssignedRole> assignedRoles;
        private List<String> facilityLimitations;
        private AuthenticationProvider authenticationProvider;
        private CustomClaims customClaims;
        private List<UserAssignedFacility> assignedFacilities;

        public Builder id(UserId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder lastLogin(Instant lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder locale(String locale) {
            this.locale = locale;
            return this;
        }

        public Builder assignedRoles(List<AssignedRole> assignedRoles) {
            this.assignedRoles = assignedRoles;
            return this;
        }

        public Builder facilityLimitations(List<String> facilityLimitations) {
            this.facilityLimitations = facilityLimitations;
            return this;
        }

        public Builder authenticationProvider(AuthenticationProvider authenticationProvider) {
            this.authenticationProvider = authenticationProvider;
            return this;
        }

        public Builder customClaims(CustomClaims customClaims) {
            this.customClaims = customClaims;
            return this;
        }

        public Builder assignedFacilities(List<UserAssignedFacility> assignedFacilities) {
            this.assignedFacilities = assignedFacilities;
            return this;
        }

        public User build() {
            return new User(id, version, created, lastModified, lastLogin, firstName, lastName,
                    username, email, status, locale, assignedRoles, facilityLimitations,
                    authenticationProvider, customClaims, assignedFacilities);
        }
    }
}
