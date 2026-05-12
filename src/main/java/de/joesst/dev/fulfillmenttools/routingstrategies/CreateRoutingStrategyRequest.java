package de.joesst.dev.fulfillmenttools.routingstrategies;

import java.util.Map;
import java.util.Objects;

public final class CreateRoutingStrategyRequest {

    private final Map<String, Object> nameLocalized;

    private CreateRoutingStrategyRequest(Builder builder) {
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
    }

    public Map<String, Object> nameLocalized() { return nameLocalized; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Map<String, Object> nameLocalized;

        public Builder nameLocalized(Map<String, Object> nameLocalized) { this.nameLocalized = nameLocalized; return this; }

        public CreateRoutingStrategyRequest build() { return new CreateRoutingStrategyRequest(this); }
    }
}
