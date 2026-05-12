package de.joesst.dev.fulfillmenttools.model;

/**
 * A user assigned to a job, identified by their username and internal user ID.
 */
public record AssignedUser(String username, String userId) {}
