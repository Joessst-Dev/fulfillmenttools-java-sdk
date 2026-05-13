package de.joesst.dev.fulfillmenttools.model;

import de.joesst.dev.fulfillmenttools.id.UserId;

/**
 * A user assigned to a job, identified by their username and internal user ID.
 *
 * @param username the username of the assigned user.
 * @param userId the internal identifier of the user.
 */
public record AssignedUser(String username, UserId userId) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String username;
        private UserId userId;

        public Builder username(String username) { this.username = username; return this; }
        public Builder userId(UserId userId) { this.userId = userId; return this; }

        public AssignedUser build() {
            return new AssignedUser(username, userId);
        }
    }
}
