package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.Map;
import java.util.Objects;

/**
 * Request for updating an existing handover job with new status or attributes.
 *
 * <p>The version field is required and used for optimistic locking to prevent concurrent
 * modifications. Use the builder to construct update requests.
 */
public final class UpdateHandoverJobRequest {

    private final Integer version;
    private final String status;
    private final Map<String, Object> customAttributes;

    /**
     * @param version The optimistic-locking version counter (required).
     * @param status The new status for the handover job.
     * @param customAttributes Free-form custom attributes to set or update.
     */
    private UpdateHandoverJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.status = builder.status;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * @return the optimistic-locking version counter
     */
    public Integer version() { return version; }

    /**
     * @return the new status for the handover job
     */
    public String status() { return status; }

    /**
     * @return free-form custom attributes
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns a new builder for constructing {@code UpdateHandoverJobRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code UpdateHandoverJobRequest}.
     *
     * <p>Allows fluent construction of update requests. The version field is required;
     * all other fields are optional.
     */
    public static final class Builder {

        private Integer version;
        private String status;
        private Map<String, Object> customAttributes;

        /**
         * @param version the optimistic-locking version counter (required)
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * @param status the new status for the handover job
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * @param customAttributes free-form custom attributes to set or update
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@code UpdateHandoverJobRequest}.
         *
         * @return the constructed request
         * @throws NullPointerException if version has not been set
         */
        public UpdateHandoverJobRequest build() { return new UpdateHandoverJobRequest(this); }
    }
}
