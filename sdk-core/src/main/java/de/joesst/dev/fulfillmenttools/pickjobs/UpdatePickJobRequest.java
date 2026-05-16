package de.joesst.dev.fulfillmenttools.pickjobs;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;
import java.util.Objects;

/**
 * Request for updating an existing pick job with new status, attributes, or picking methods.
 *
 * <p>The version field is required and used for optimistic locking to prevent concurrent
 * modifications. Use the builder to construct update requests.
 */
public final class UpdatePickJobRequest {

    private final Integer version;
    private final String status;
    private final CustomAttributes customAttributes;
    private final List<String> preferredPickingMethods;

    /**
     * @param version The optimistic-locking version counter (required).
     * @param status The new status for the pick job.
     * @param customAttributes Free-form custom attributes to set or update.
     * @param preferredPickingMethods Preferred picking methods (e.g. {@code "PICK_BY_ARTICLE"}).
     */
    private UpdatePickJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.status = builder.status;
        this.customAttributes = builder.customAttributes;
        this.preferredPickingMethods = builder.preferredPickingMethods;
    }

    /**
     * Returns the optimistic-locking version counter.
     *
     * @return the version counter
     */
    public Integer version() { return version; }

    /**
     * Returns the new status for the pick job.
     *
     * @return the status, or null if not set
     */
    public String status() { return status; }

    /**
     * Returns free-form custom attributes.
     *
     * @return the custom attributes map, or null if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns the preferred picking methods.
     *
     * @return the picking methods list, or null if not set
     */
    public List<String> preferredPickingMethods() { return preferredPickingMethods; }

    /**
     * Returns a new builder for constructing {@code UpdatePickJobRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code UpdatePickJobRequest}.
     *
     * <p>Allows fluent construction of update requests. The version field is required;
     * all other fields are optional.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private Integer version;
        private String status;
        private CustomAttributes customAttributes;
        private List<String> preferredPickingMethods;

        /**
         * Sets the optimistic-locking version counter (required).
         *
         * @param version the version counter
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the new status for the pick job.
         *
         * @param status the status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets free-form custom attributes.
         *
         * @param customAttributes the custom attributes to set or update
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Sets the preferred picking methods.
         *
         * @param preferredPickingMethods the picking methods
         * @return this builder
         */
        public Builder preferredPickingMethods(List<String> preferredPickingMethods) { this.preferredPickingMethods = preferredPickingMethods; return this; }

        /**
         * Builds the {@code UpdatePickJobRequest}.
         *
         * @return the constructed request
         * @throws NullPointerException if version has not been set
         */
        public UpdatePickJobRequest build() { return new UpdatePickJobRequest(this); }
    }
}
