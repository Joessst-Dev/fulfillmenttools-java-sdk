package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.id.UserId;

import java.time.Instant;

/**
 * A record of a single user modification event on a pick job.
 *
 * <p>Maps to the {@code UserModificationHistory} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param modificationDate Timestamp when the modification occurred.
 * @param userId           ID of the user who made the modification.
 * @param username         Username of the user who made the modification.
 */
public record UserModificationHistory(
        Instant modificationDate,
        UserId userId,
        String username
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant modificationDate;
        private UserId userId;
        private String username;

        private Builder() {}

        public Builder modificationDate(Instant modificationDate) { this.modificationDate = modificationDate; return this; }
        public Builder userId(UserId userId) { this.userId = userId; return this; }
        public Builder username(String username) { this.username = username; return this; }

        public UserModificationHistory build() {
            return new UserModificationHistory(modificationDate, userId, username);
        }
    }
}
