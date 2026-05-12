package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import java.util.List;
import java.util.Objects;

public final class CreateFacilityDiscountRequest {

    private final String type;
    private final Integer priority;
    private final FacilityDiscountValue discount;
    private final List<FacilityDiscountContext> context;

    private CreateFacilityDiscountRequest(Builder builder) {
        this.type = Objects.requireNonNull(builder.type, "type");
        this.priority = Objects.requireNonNull(builder.priority, "priority");
        this.discount = Objects.requireNonNull(builder.discount, "discount");
        this.context = builder.context;
    }

    public String type() { return type; }
    public Integer priority() { return priority; }
    public FacilityDiscountValue discount() { return discount; }
    public List<FacilityDiscountContext> context() { return context; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String type;
        private Integer priority;
        private FacilityDiscountValue discount;
        private List<FacilityDiscountContext> context;

        public Builder type(String type) { this.type = type; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder discount(FacilityDiscountValue discount) { this.discount = discount; return this; }
        public Builder context(List<FacilityDiscountContext> context) { this.context = context; return this; }
        public CreateFacilityDiscountRequest build() { return new CreateFacilityDiscountRequest(this); }
    }
}
