package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.Map;

/**
 * A configured cancellation reason on a read-side order.
 *
 * <p>Maps to the {@code CancelationReason} schema in the fulfillmenttools OpenAPI spec.
 * Extends {@code AbstractReason} which extends {@code VersionedResource}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id              The platform-generated identifier of the cancellation reason.
 * @param version         The optimistic-locking version.
 * @param created         The timestamp when this reason was created.
 * @param lastModified    The timestamp of the last modification.
 * @param action          The action this reason is attached to; always {@code CANCELATION}.
 * @param reason          The human-readable reason text.
 * @param reasonLocalized Localized translations for the reason, keyed by locale.
 */
public record CancelationReason(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String action,
        String reason,
        Map<String, String> reasonLocalized
) {

    /**
     * Returns a builder for constructing a {@code CancelationReason}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CancelationReason}.
     */
    public static final class Builder {

        private String id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String action;
        private String reason;
        private Map<String, String> reasonLocalized;

        private Builder() {}

        /**
         * Sets the platform-generated identifier of the cancellation reason.
         * @param id the identifier
         * @return this builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the optimistic-locking version.
         * @param version the version number
         * @return this builder
         */
        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        /**
         * Sets the timestamp when this reason was created.
         * @param created the creation timestamp
         * @return this builder
         */
        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        /**
         * Sets the timestamp of the last modification.
         * @param lastModified the last-modified timestamp
         * @return this builder
         */
        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        /**
         * Sets the action this reason is attached to.
         * @param action the action (e.g. {@code CANCELATION})
         * @return this builder
         */
        public Builder action(String action) {
            this.action = action;
            return this;
        }

        /**
         * Sets the human-readable reason text.
         * @param reason the reason text
         * @return this builder
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * Sets the localized translations for the reason, keyed by locale.
         * @param reasonLocalized map of locale to localized reason text
         * @return this builder
         */
        public Builder reasonLocalized(Map<String, String> reasonLocalized) {
            this.reasonLocalized = reasonLocalized;
            return this;
        }

        /**
         * Builds a {@link CancelationReason}.
         *
         * @return a new instance.
         */
        public CancelationReason build() {
            return new CancelationReason(id, version, created, lastModified, action, reason, reasonLocalized);
        }
    }
}
