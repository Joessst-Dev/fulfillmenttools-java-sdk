package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.Objects;

public final class UpdateRoutingPlanRequest {

    private final Integer version;
    private final String facilityRef;
    private final String status;

    private UpdateRoutingPlanRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
    }

    public Integer version() { return version; }
    public String facilityRef() { return facilityRef; }
    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private String facilityRef;
        private String status;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public UpdateRoutingPlanRequest build() { return new UpdateRoutingPlanRequest(this); }
    }
}
