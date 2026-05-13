package de.joesst.dev.fulfillmenttools.eventing;

/**
 * Request to update an existing subscription.
 */
public final class UpdateSubscriptionRequest {

    private final String callbackUrl;
    private final String status;

    private UpdateSubscriptionRequest(Builder builder) {
        this.callbackUrl = builder.callbackUrl;
        this.status = builder.status;
    }

    /**
     * Returns the callback URL for webhook delivery.
     * @return the callback URL
     */
    public String callbackUrl() { return callbackUrl; }

    /**
     * Returns the subscription status.
     * @return the subscription status
     */
    public String status() { return status; }

    /**
     * Creates a new builder for UpdateSubscriptionRequest.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing UpdateSubscriptionRequest instances.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private String callbackUrl;
        private String status;

        /**
         * Sets the callback URL for webhook delivery.
         * @param callbackUrl the callback URL
         * @return this builder
         */
        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }

        /**
         * Sets the subscription status.
         * @param status the subscription status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Builds and returns an UpdateSubscriptionRequest instance.
         * @return a new UpdateSubscriptionRequest
         */
        public UpdateSubscriptionRequest build() { return new UpdateSubscriptionRequest(this); }
    }
}
