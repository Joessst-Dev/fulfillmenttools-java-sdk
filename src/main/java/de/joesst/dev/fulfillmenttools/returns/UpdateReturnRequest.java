package de.joesst.dev.fulfillmenttools.returns;

/**
 * Request to update an existing return.
 */
public final class UpdateReturnRequest {

    private final String status;

    private UpdateReturnRequest(Builder builder) {
        this.status = builder.status;
    }

    /**
     * Returns the new status for the return.
     *
     * @return the status, or null if not set
     */
    public String status() { return status; }

    /**
     * Creates a new builder for constructing an {@code UpdateReturnRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link UpdateReturnRequest}.
     */
    public static final class Builder {

        private String status;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the new status for the return.
         *
         * @param status the status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Builds the {@link UpdateReturnRequest}.
         *
         * @return a new request
         */
        public UpdateReturnRequest build() { return new UpdateReturnRequest(this); }
    }
}
