package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request parameters for updating an existing stow job via
 * {@link InboundClient#update(String, UpdateStowJobRequest)}.
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
    private final Map<String, Object> customAttributes;

    private UpdateStowJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.priority = builder.priority;
        this.targetTime = builder.targetTime;
        this.assignedUsers = builder.assignedUsers;
        this.customAttributes = builder.customAttributes;
    }

    /** The current version of the stow job; used for optimistic locking. */
    public Integer version() { return version; }

    /** New priority level of the job. */
    public Integer priority() { return priority; }

    /** New target completion time for the job. */
    public Instant targetTime() { return targetTime; }

    /** Replacement list of users assigned to the stow job (at most 20). */
    public List<AssignedUserInput> assignedUsers() { return assignedUsers; }

    /** Free-form custom attributes. */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /** Returns a new {@link Builder} for constructing an {@code UpdateStowJobRequest}. */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link UpdateStowJobRequest}.
     */
    public static final class Builder {

        private Integer version;
        private Integer priority;
        private Instant targetTime;
        private List<AssignedUserInput> assignedUsers;
        private Map<String, Object> customAttributes;

        /** @see UpdateStowJobRequest#version() */
        public Builder version(Integer version) { this.version = version; return this; }

        /** @see UpdateStowJobRequest#priority() */
        public Builder priority(Integer priority) { this.priority = priority; return this; }

        /** @see UpdateStowJobRequest#targetTime() */
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }

        /** @see UpdateStowJobRequest#assignedUsers() */
        public Builder assignedUsers(List<AssignedUserInput> assignedUsers) { this.assignedUsers = assignedUsers; return this; }

        /** @see UpdateStowJobRequest#customAttributes() */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /** Builds the {@link UpdateStowJobRequest}. Throws {@link NullPointerException} if version is absent. */
        public UpdateStowJobRequest build() { return new UpdateStowJobRequest(this); }
    }
}
