package de.joesst.dev.fulfillmenttools.model;

/**
 * A user assigned to a job, identified by their username and internal user ID.
 *
 * @param username the username of the assigned user.
 * @param userId the internal identifier of the user.
 */
public record AssignedUser(String username, String userId) {}
