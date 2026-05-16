package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Request parameters for updating an existing stow job via
 * {@link InboundClient#update(de.joesst.dev.fulfillmenttools.id.StowJobId, UpdateStowJobRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * UpdateStowJobRequest request = UpdateStowJobRequest.builder()
 *     .version(2)
 *     .priority(5)
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class UpdateStowJobRequest {

    private final Integer version;
    private final Integer priority;
    private final Instant targetTime;
    private final List<AssignedUserInput> assignedUsers;
    private final CustomAttributes customAttributes;

    private UpdateStowJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.priority = builder.priority;
        this.targetTime = builder.targetTime;
        this.assignedUsers = builder.assignedUsers;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the current version of the stow job; used for optimistic locking.
     * @return the version; never {@code null}
     */
    public Integer version() { return version; }

    /**
     * Returns the new priority level of the job.
     * @return the priority, or {@code null} if not set
     */
    public Integer priority() { return priority; }

    /**
     * Returns the new target completion time for the job.
     * @return the target time, or {@code null} if not set
     */
    public Instant targetTime() { return targetTime; }

    /**
     * Returns the replacement list of users assigned to the stow job.
     * @return the assigned users, or {@code null} if not set
     */
    public List<AssignedUserInput> assignedUsers() { return assignedUsers; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Returns a new {@link Builder} for constructing an {@code UpdateStowJobRequest}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link UpdateStowJobRequest}.
     */
    public static final class Builder {

        private Integer version;
        private Integer priority;
        private Instant targetTime;
        private List<AssignedUserInput> assignedUsers;
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
         * Sets the new priority level of the job.
         * @param priority the priority
         * @return this builder
         */
        public Builder priority(Integer priority) { this.priority = priority; return this; }

        /**
         * Sets the new target completion time for the job.
         * @param targetTime the target time
         * @return this builder
         */
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }

        /**
         * Sets the replacement list of users assigned to the stow job.
         * @param assignedUsers the assigned users
         * @return this builder
         */
        public Builder assignedUsers(List<AssignedUserInput> assignedUsers) { this.assignedUsers = assignedUsers; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link UpdateStowJobRequest}.
         * @return a new {@code UpdateStowJobRequest}
         * @throws NullPointerException if version is absent
         */
        public UpdateStowJobRequest build() { return new UpdateStowJobRequest(this); }
    }
}
