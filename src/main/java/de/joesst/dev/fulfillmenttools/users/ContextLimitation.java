package de.joesst.dev.fulfillmenttools.users;

import java.util.List;

/**
 * A context limitation that constrains a user's role to specific resources.
 *
 * <p>A {@code ContextLimitation} restricts a {@link AssignedRole} or {@link UserRole}
 * to a set of resource identifiers of a given type (e.g., specific facilities or zones).
 *
 * @param type   the kind of resource this limitation applies to; one of
 *               {@code FACILITY}, {@code FACILITY_GROUP}, {@code ROLE},
 *               {@code USER_ROLE}, {@code ZONE}
 * @param values the list of resource identifiers to which the limitation is scoped
 */
public record ContextLimitation(
        String type,
        List<String> values
) {}
