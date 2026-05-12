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

    /** Reference to the facility where the stow job will be executed. */
    public String facilityRef() { return facilityRef; }

    /** Initial status of the stow job (e.g. {@code OPEN}). */
    public String status() { return status; }

    /** The stow line items that should be processed. */
    public List<StowLineItemForCreation> stowLineItems() { return stowLineItems; }

    /** Users to assign to the stow job (at most 20). */
    public List<AssignedUserInput> assignedUsers() { return assignedUsers; }

    /** Free-form custom attributes. */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /** Priority level of the job. */
    public Integer priority() { return priority; }

    /** Human-readable short identifier for display purposes. */
    public String shortId() { return shortId; }

    /** The time by which the job should be completed. */
    public Instant targetTime() { return targetTime; }

    /** Returns a new {@link Builder} for constructing a {@code CreateStowJobRequest}. */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateStowJobRequest}.
     */
    public static final class Builder {

        private String facilityRef;
        private String status;
        private List<StowLineItemForCreation> stowLineItems;
        private List<AssignedUserInput> assignedUsers;
        private Map<String, Object> customAttributes;
        private Integer priority;
        private String shortId;
        private Instant targetTime;

        /** @see CreateStowJobRequest#facilityRef() */
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        /** @see CreateStowJobRequest#status() */
        public Builder status(String status) { this.status = status; return this; }

        /** @see CreateStowJobRequest#stowLineItems() */
        public Builder stowLineItems(List<StowLineItemForCreation> stowLineItems) { this.stowLineItems = stowLineItems; return this; }

        /** @see CreateStowJobRequest#assignedUsers() */
        public Builder assignedUsers(List<AssignedUserInput> assignedUsers) { this.assignedUsers = assignedUsers; return this; }

        /** @see CreateStowJobRequest#customAttributes() */
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        /** @see CreateStowJobRequest#priority() */
        public Builder priority(Integer priority) { this.priority = priority; return this; }

        /** @see CreateStowJobRequest#shortId() */
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }

        /** @see CreateStowJobRequest#targetTime() */
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }

        /** Builds the {@link CreateStowJobRequest}. Throws {@link NullPointerException} if any required field is absent. */
        public CreateStowJobRequest build() { return new CreateStowJobRequest(this); }
    }
}
