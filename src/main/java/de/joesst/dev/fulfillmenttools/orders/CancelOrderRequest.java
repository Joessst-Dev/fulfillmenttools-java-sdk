package de.joesst.dev.fulfillmenttools.orders;

import java.util.Objects;

/**
 * Request to cancel an existing order.
 */
public final class CancelOrderRequest {

    private final Integer version;
    private final String cancelationReasonId;

    private CancelOrderRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.cancelationReasonId = builder.cancelationReasonId;
    }

    /**
     * Returns the version of the order to cancel.
     *
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns the cancellation reason ID.
     *
     * @return the cancellation reason ID, or null if not set
     */
    public String cancelationReasonId() { return cancelationReasonId; }

    /**
     * Creates a new builder for constructing a {@code CancelOrderRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link CancelOrderRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private Integer version;
        private String cancelationReasonId;

        /**
         * Sets the version of the order to cancel.
         *
         * @param version the version (required)
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the cancellation reason ID.
         *
         * @param cancelationReasonId the reason ID, or null
         * @return this builder
         */
        public Builder cancelationReasonId(String cancelationReasonId) { this.cancelationReasonId = cancelationReasonId; return this; }

        /**
         * Builds the {@link CancelOrderRequest}.
         *
         * @return a new request
         */
        public CancelOrderRequest build() { return new CancelOrderRequest(this); }
    }
}
