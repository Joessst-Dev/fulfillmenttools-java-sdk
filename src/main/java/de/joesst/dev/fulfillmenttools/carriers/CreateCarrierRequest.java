package de.joesst.dev.fulfillmenttools.carriers;

import java.util.Objects;

public final class CreateCarrierRequest {

    private final String name;

    private CreateCarrierRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
    }

    public String name() { return name; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;

        public Builder name(String name) { this.name = name; return this; }

        public CreateCarrierRequest build() { return new CreateCarrierRequest(this); }
    }
}
