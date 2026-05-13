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
) {}
