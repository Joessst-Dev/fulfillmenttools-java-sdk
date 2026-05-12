package de.joesst.dev.fulfillmenttools.storagelocations;

public final class StorageLocationListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String scannableCode;

    private StorageLocationListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.scannableCode = builder.scannableCode;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String scannableCode() { return scannableCode; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.scannableCode = this.scannableCode;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String scannableCode;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder scannableCode(String scannableCode) { this.scannableCode = scannableCode; return this; }

        public StorageLocationListRequest build() { return new StorageLocationListRequest(this); }
    }
}
