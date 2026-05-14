package de.joesst.dev.fulfillmenttools.users;

import java.util.List;

/**
 * Deprecated custom claims attached to a user.
 *
 * <p>This type is kept for deserialization compatibility with existing users. New code
 * should use {@link AssignedRole} via {@link User#assignedRoles()} instead.
 *
 * @param roles the list of user role assignments; required
 * @deprecated Use {@link User#assignedRoles()} instead.
 */
@Deprecated
public record CustomClaims(
        List<UserRole> roles
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private List<UserRole> roles;

        public Builder roles(List<UserRole> roles) {
            this.roles = roles;
            return this;
        }

        public CustomClaims build() {
            return new CustomClaims(roles);
        }
    }
}
