package de.joesst.dev.fulfillmenttools.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.UserId;

/**
 * Specifies a user to assign to a stow job in a create or update request.
 *
 * <p>The fulfillmenttools API accepts three forms of user reference via an {@code anyOf}:
 * <ul>
 *   <li>By user ID — supply only {@code userId}</li>
 *   <li>By username — supply only {@code username}</li>
 *   <li>Full assigned user — supply both {@code userId} and {@code username}</li>
 * </ul>
 *
 * <p>Maps to the {@code AssignedUserForCreationById}, {@code AssignedUserForCreationByName},
 * and {@code AssignedUser} anyOf union in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param userId   The internal user ID. Supply alone to reference a user by ID.
 * @param username The username. Supply alone to reference a user by name.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AssignedUserInput(
        UserId userId,
        String username
) {

    /**
     * Creates an {@code AssignedUserInput} that references a user by their internal ID.
     *
     * @param userId the internal user ID; must not be {@code null}
     * @return a new {@code AssignedUserInput}
     */
    public static AssignedUserInput byId(UserId userId) {
        return new AssignedUserInput(userId, null);
    }

    /**
     * Creates an {@code AssignedUserInput} that references a user by their username.
     *
     * @param username the username; must not be {@code null}
     * @return a new {@code AssignedUserInput}
     */
    public static AssignedUserInput byName(String username) {
        return new AssignedUserInput(null, username);
    }

    /**
     * Creates an {@code AssignedUserInput} that supplies both user ID and username.
     *
     * @param userId   the internal user ID; must not be {@code null}
     * @param username the username; must not be {@code null}
     * @return a new {@code AssignedUserInput}
     */
    public static AssignedUserInput of(UserId userId, String username) {
        return new AssignedUserInput(userId, username);
    }
}
