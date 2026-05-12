package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;
import java.util.Objects;

public final class CreateFacilityRequest {

    private final String name;
    private final String tenantFacilityId;
    private final String status;
    private final String type;
    private final Map<String, Object> customAttributes;

    private CreateFacilityRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.tenantFacilityId = builder.tenantFacilityId;
        this.status = builder.status;
        this.type = builder.type;
        this.customAttributes = builder.customAttributes;
    }

    public String name() { return name; }
    public String tenantFacilityId() { return tenantFacilityId; }
    public String status() { return status; }
    public String type() { return type; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String tenantFacilityId;
        private String status;
        private String type;
        private Map<String, Object> customAttributes;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tenantFacilityId(String tenantFacilityId) {
            this.tenantFacilityId = tenantFacilityId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public CreateFacilityRequest build() { return new CreateFacilityRequest(this); }
    }
}
