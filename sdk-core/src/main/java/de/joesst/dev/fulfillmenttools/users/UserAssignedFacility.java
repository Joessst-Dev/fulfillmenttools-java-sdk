package de.joesst.dev.fulfillmenttools.users;

import de.joesst.dev.fulfillmenttools.id.FacilityId;

/**
 * A facility directly assigned to a user.
 *
 * <p>This type represents the deprecated {@code assignedFacilities} field on {@link User}.
 * New integrations should use {@link AssignedRole} with {@link ContextLimitation} instead.
 *
 * @param facilityRef the facility reference identifier
 * @deprecated Use {@link User#assignedRoles()} with context limitations instead.
 */
@Deprecated
public record UserAssignedFacility(
        FacilityId facilityRef
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private FacilityId facilityRef;

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public UserAssignedFacility build() {
            return new UserAssignedFacility(facilityRef);
        }
    }
}
