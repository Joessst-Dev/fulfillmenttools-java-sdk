package de.joesst.dev.fulfillmenttools.inbound;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class UpdateStowJobRequest {

    private final Integer version;
    private final Integer priority;
    private final Instant targetTime;
    private final List<Map<String, Object>> assignedUsers;
    private final Map<String, Object> customAttributes;

    private UpdateStowJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.priority = builder.priority;
        this.targetTime = builder.targetTime;
        this.assignedUsers = builder.assignedUsers;
        this.customAttributes = builder.customAttributes;
    }

    public Integer version() { return version; }
    public Integer priority() { return priority; }
    public Instant targetTime() { return targetTime; }
    public List<Map<String, Object>> assignedUsers() { return assignedUsers; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private Integer priority;
        private Instant targetTime;
        private List<Map<String, Object>> assignedUsers;
        private Map<String, Object> customAttributes;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder targetTime(Instant targetTime) { this.targetTime = targetTime; return this; }
        public Builder assignedUsers(List<Map<String, Object>> assignedUsers) { this.assignedUsers = assignedUsers; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdateStowJobRequest build() { return new UpdateStowJobRequest(this); }
    }
}
