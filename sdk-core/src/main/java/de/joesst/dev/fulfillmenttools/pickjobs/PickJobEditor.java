package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.id.UserId;

/**
 * Identifies the user who currently has the pick job open for editing.
 *
 * <p>Maps to the {@code Editor} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param userId   ID of the user who is editing the pick job.
 * @param username Username of the user who is editing the pick job.
 */
public record PickJobEditor(
        UserId userId,
        String username
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UserId userId;
        private String username;

        private Builder() {}

        public Builder userId(UserId userId) { this.userId = userId; return this; }
        public Builder username(String username) { this.username = username; return this; }

        public PickJobEditor build() {
            return new PickJobEditor(userId, username);
        }
    }
}
