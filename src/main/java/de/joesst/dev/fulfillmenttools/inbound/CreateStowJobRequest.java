package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request parameters for creating a new stow job via
 * {@link InboundClient#create(CreateStowJobRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * CreateStowJobRequest request = CreateStowJobRequest.builder()
 *     .facilityRef("fac-1")
 *     .status("OPEN")
 *     .stowLineItems(List.of(...))
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class CreateStowJobRequest {

    private final String facilityRef;
    private final String status;
    private final List<StowLineItemForCreation> stowLineItems;
    private final List<AssignedUserInput> assignedUsers;
    private final Map<String, Object> customAttributes;
    private final Integer priority;
    private final String shortId;
    private final Instant targetTime;

    private CreateStowJobRequest(Builder builder) {
        this.facilityRef = Objects.requireNonNull(builder.facilityRef, "facilityRef must not be null");
        this.status = Objects.requireNonNull(builder.status, "status must not be null");
        this.stowLineItems = Objects.requireNonNull(builder.stowLineItems, "stowLineItems must not be null");
        this.assignedUsers = builder.assignedUsers;
        this.customAttributes = builder.customAttributes;
        this.priority = builder.priority;
        this.shortId = builder.shortId;
        this.targetTime = builder.targetTime;
    }

    /**
     * Returns the facility reference.
     * @return the facility ref; never {@code null}
     */
    public String facilityRef() { return facilityRef; }

    /**
     * Returns the initial status of the stow job.
     * @return the status (e.g. {@code OPEN}); never {@code null}
     */
    public String status() { return status; }

    /**
     * Returns the stow line items to process.
     * @return the stow line items; never {@code null}
     */
    public List<StowLineItemForCreation> stowLineItems() { return stowLineItems; }

    /**
     * Returns the users assigned to the stow job.
     * @return the assigned users, or {@code null} if not set
     */
    public List<AssignedUserInput> assignedUsers() { return assignedUsers; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Returns the priority level of the job.
     * @return the priority, or {@code null} if not set
     */
    public Integer priority() { return priority; }

    /**
     * Returns the human-readable short identifier.
     * @return the short ID, or {@code null} if not set
     */
    public String shortId() { return shortId; }

    /**
     * Returns the time by which the job should be completed.
     * @return the target time, or {@code null} if not set
     */
    public Instant targetTime() { return targetTime; }

    /**
     * Creates a new builder for constructing a {@link CreateStowJobRequest}.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateStowJobRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String facilityRef;
        private String status;
        private List<StowLineItemForCreation> stowLineItems;
        private List<AssignedUserInput> assignedUsers;
        private Map<String, Object> customAttributes;
        private Integer priority;
        private String shortId;
        private Instant targetTime;

        /**
         * Sets the facility reference (required).
         * @param facilityRef the facility ref
         * @return this builder
         */
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the initial status (required).
         * @param status the initial status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Sets the stow line items (required).
         * @param stowLineItems the stow line items
         * @return this builder
         */
        public Builder stowLineItems(List<StowLineItemForCreation> stowLineItems) { this.stowLineItems = stowLineItems; return this; }

        /**
         * Sets the users assigned to the job.
         * @param assignedUsers the assigned users
         * @return this builder
         */
        public Builder assignedUsers(List<AssignedUserInput> assignedUsers) { this.assignedUsers = assignedUsers; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Sets the priority level.
         * @param priority the priority
         * @return this builder
         */
        public Builder priority(Integer priority) { this.priority = priority; return this; }

        /**
         * Sets the short identifier.
         * @param shortId the short ID
         * @return this builder
         */
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }

        /**
         * Sets the target completion time.
         * @param targetTime the target time
         * @return this builder
         */
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }

        /**
         * Builds and returns a new {@link CreateStowJobRequest}.
         * @return a new request instance
         * @throws NullPointerException if any required field is not set
         */
        public CreateStowJobRequest build() { return new CreateStowJobRequest(this); }
    }
}
