package de.joesst.dev.fulfillmenttools.users;

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
        String facilityRef
) {}
