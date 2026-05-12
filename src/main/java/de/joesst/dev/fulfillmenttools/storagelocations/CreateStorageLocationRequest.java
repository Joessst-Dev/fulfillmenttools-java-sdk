package de.joesst.dev.fulfillmenttools.storagelocations;

import java.util.Objects;

public final class CreateStorageLocationRequest {

    private final String name;
    private final String type;

    private CreateStorageLocationRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.type = builder.type;
    }

    public String name() { return name; }
    public String type() { return type; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String type;

        public Builder name(String name) { this.name = name; return this; }
        public Builder type(String type) { this.type = type; return this; }

        public CreateStorageLocationRequest build() { return new CreateStorageLocationRequest(this); }
    }
}
