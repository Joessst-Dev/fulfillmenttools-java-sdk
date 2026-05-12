package de.joesst.dev.fulfillmenttools.storagelocations;

public final class StorageLocationListRequest {

    private final Integer size;
    private final String startAfterId;

    private StorageLocationListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        public StorageLocationListRequest build() { return new StorageLocationListRequest(this); }
    }
}
