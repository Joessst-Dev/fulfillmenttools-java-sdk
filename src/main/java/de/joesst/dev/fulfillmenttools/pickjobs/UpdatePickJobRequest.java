package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class UpdatePickJobRequest {

    private final Integer version;
    private final String status;
    private final Map<String, Object> customAttributes;
    private final List<String> preferredPickingMethods;

    private UpdatePickJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.status = builder.status;
        this.customAttributes = builder.customAttributes;
        this.preferredPickingMethods = builder.preferredPickingMethods;
    }

    public Integer version() { return version; }
    public String status() { return status; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public List<String> preferredPickingMethods() { return preferredPickingMethods; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private String status;
        private Map<String, Object> customAttributes;
        private List<String> preferredPickingMethods;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder preferredPickingMethods(List<String> preferredPickingMethods) { this.preferredPickingMethods = preferredPickingMethods; return this; }

        public UpdatePickJobRequest build() { return new UpdatePickJobRequest(this); }
    }
}
