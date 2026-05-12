package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import java.util.List;
import java.util.Objects;

public final class UpdateFacilityDiscountRequest {

    private final Integer version;
    private final String type;
    private final Integer priority;
    private final FacilityDiscountValue discount;
    private final List<FacilityDiscountContext> context;

    private UpdateFacilityDiscountRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version");
        this.type = builder.type;
        this.priority = builder.priority;
        this.discount = builder.discount;
        this.context = builder.context;
    }

    public Integer version() { return version; }
    public String type() { return type; }
    public Integer priority() { return priority; }
    public FacilityDiscountValue discount() { return discount; }
    public List<FacilityDiscountContext> context() { return context; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer version;
        private String type;
        private Integer priority;
        private FacilityDiscountValue discount;
        private List<FacilityDiscountContext> context;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder discount(FacilityDiscountValue discount) { this.discount = discount; return this; }
        public Builder context(List<FacilityDiscountContext> context) { this.context = context; return this; }
        public UpdateFacilityDiscountRequest build() { return new UpdateFacilityDiscountRequest(this); }
    }
}
