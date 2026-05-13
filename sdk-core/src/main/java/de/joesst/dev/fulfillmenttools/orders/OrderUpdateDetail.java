package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;

/**
 * Represents details of a single order update event.
 *
 * @param created the instant when this update was created
 * @param changes the changes made in this update
 * @param comment an optional comment explaining the update
 * @param user the user who made the update
 */
public record OrderUpdateDetail(
        Instant created,
        OrderUpdateDetailChanges changes,
        String comment,
        String user
) {

    /**
     * Returns a builder for constructing an {@code OrderUpdateDetail}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OrderUpdateDetail}.
     */
    public static final class Builder {

        private Instant created;
        private OrderUpdateDetailChanges changes;
        private String comment;
        private String user;

        private Builder() {}

        /**
         * Sets the instant when this update was created.
         * @param created the creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the changes made in this update.
         * @param changes the update changes
         * @return this builder
         */
        public Builder changes(OrderUpdateDetailChanges changes) {
            this.changes = changes;
            return this;
        }

        /**
         * Sets the optional comment explaining the update.
         * @param comment the comment text
         * @return this builder
         */
        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        /**
         * Sets the user who made the update.
         * @param user the user identifier
         * @return this builder
         */
        public Builder user(String user) {
            this.user = user;
            return this;
        }

        /**
         * Builds an {@link OrderUpdateDetail}.
         *
         * @return a new instance.
         */
        public OrderUpdateDetail build() {
            return new OrderUpdateDetail(created, changes, comment, user);
        }
    }
}
