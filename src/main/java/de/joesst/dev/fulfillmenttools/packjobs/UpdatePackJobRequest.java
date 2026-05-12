package de.joesst.dev.fulfillmenttools.packjobs;

import java.util.Map;
import java.util.Objects;

public final class UpdatePackJobRequest {

    private final Integer version;
    private final String status;
    private final Map<String, Object> customAttributes;

    private UpdatePackJobRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.status = builder.status;
        this.customAttributes = builder.customAttributes;
    }

    public Integer version() { return version; }
    public String status() { return status; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private String status;
        private Map<String, Object> customAttributes;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdatePackJobRequest build() { return new UpdatePackJobRequest(this); }
    }
}
