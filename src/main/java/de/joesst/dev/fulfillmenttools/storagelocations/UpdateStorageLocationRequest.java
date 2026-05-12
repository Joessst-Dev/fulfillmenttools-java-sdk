package de.joesst.dev.fulfillmenttools.storagelocations;

public final class UpdateStorageLocationRequest {

    private final String name;
    private final String type;

    private UpdateStorageLocationRequest(Builder builder) {
        this.name = builder.name;
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

        public UpdateStorageLocationRequest build() { return new UpdateStorageLocationRequest(this); }
    }
}
