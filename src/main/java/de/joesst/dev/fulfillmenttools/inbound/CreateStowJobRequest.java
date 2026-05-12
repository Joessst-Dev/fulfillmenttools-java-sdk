package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateStowJobRequest {

    private final String facilityRef;
    private final String status;
    private final List<Map<String, Object>> stowLineItems;
    private final List<Map<String, Object>> assignedUsers;
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

    public String facilityRef() { return facilityRef; }
    public String status() { return status; }
    public List<Map<String, Object>> stowLineItems() { return stowLineItems; }
    public List<Map<String, Object>> assignedUsers() { return assignedUsers; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public Integer priority() { return priority; }
    public String shortId() { return shortId; }
    public Instant targetTime() { return targetTime; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String facilityRef;
        private String status;
        private List<Map<String, Object>> stowLineItems;
        private List<Map<String, Object>> assignedUsers;
        private Map<String, Object> customAttributes;
        private Integer priority;
        private String shortId;
        private Instant targetTime;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder stowLineItems(List<Map<String, Object>> stowLineItems) { this.stowLineItems = stowLineItems; return this; }
        public Builder assignedUsers(List<Map<String, Object>> assignedUsers) { this.assignedUsers = assignedUsers; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }

        public CreateStowJobRequest build() { return new CreateStowJobRequest(this); }
    }
}
