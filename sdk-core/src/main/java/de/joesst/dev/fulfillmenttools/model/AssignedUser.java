package de.joesst.dev.fulfillmenttools.model;

import de.joesst.dev.fulfillmenttools.id.UserId;

/**
 * A user assigned to a job, identified by their username and internal user ID.
 *
 * @param username the username of the assigned user.
 * @param userId the internal identifier of the user.
 */
public record AssignedUser(String username, UserId userId) {}
