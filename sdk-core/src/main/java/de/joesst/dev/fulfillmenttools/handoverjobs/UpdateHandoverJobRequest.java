package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Request for updating an existing handover job with new status or attributes.
 *
 * <p>The version field is required and used for optimistic locking to prevent concurrent
 * modifications. Use the builder to construct update requests.
 */
public final class UpdateHandoverJobRequest {

    private final Integer version;
    private final String status;
    private final CustomAttributes customAttributes;

    private UpdateHandoverJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.status = builder.status;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the optimistic-locking version counter.
     * @return the version counter; never {@code null}
     */
    public Integer version() { return version; }

    /**
     * Returns the new status for the handover job.
     * @return the status, or {@code null} if not set
     */
    public String status() { return status; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns a new builder for constructing {@code UpdateHandoverJobRequest} instances.
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
        private CustomAttributes customAttributes;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the optimistic-locking version counter (required).
         * @param version the version counter
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the new status for the handover job.
         * @param status the status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets the free-form custom attributes to set or update.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@code UpdateHandoverJobRequest}.
         * @return the constructed request
         * @throws NullPointerException if version has not been set
         */
        public UpdateHandoverJobRequest build() { return new UpdateHandoverJobRequest(this); }
    }
}
