package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Objects;

public final class CreateFacilityRequest {

    private final String name;
    private final String tenantFacilityId;

    private CreateFacilityRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.tenantFacilityId = builder.tenantFacilityId;
    }

    public String name() { return name; }
    public String tenantFacilityId() { return tenantFacilityId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String tenantFacilityId;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tenantFacilityId(String tenantFacilityId) {
            this.tenantFacilityId = tenantFacilityId;
            return this;
        }

        public CreateFacilityRequest build() { return new CreateFacilityRequest(this); }
    }
}
