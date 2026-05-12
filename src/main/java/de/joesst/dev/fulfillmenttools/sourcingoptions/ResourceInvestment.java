package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.Objects;

public final class ResourceInvestment {

    private final Double level;

    private ResourceInvestment(Builder builder) {
        this.level = Objects.requireNonNull(builder.level, "level must not be null");
    }

    public Double level() { return level; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Double level;

        public Builder level(Double level) {
            this.level = level;
            return this;
        }

        public ResourceInvestment build() { return new ResourceInvestment(this); }
    }
}
