package de.joesst.dev.fulfillmenttools.storagelocations;

/**
 * Request parameters for listing storage locations via
 * {@link StorageLocationsClient#list(de.joesst.dev.fulfillmenttools.id.FacilityId, StorageLocationListRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * StorageLocationListRequest request = StorageLocationListRequest.builder()
 *     .size(50)
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class StorageLocationListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String scannableCode;

    private StorageLocationListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.scannableCode = builder.scannableCode;
    }

    /**
     * Returns the maximum number of storage locations to return per page.
     *
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination, indicating where to start fetching results.
     *
     * @return the start-after ID, or {@code null} if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the scannable code to filter by.
     *
     * @return the scannable code, or {@code null} if not set
     */
    public String scannableCode() { return scannableCode; }

    /**
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.scannableCode = this.scannableCode;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code StorageLocationListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link StorageLocationListRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Integer size;
        private String startAfterId;
        private String scannableCode;

        /**
         * Sets the maximum number of storage locations to return per page.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination, indicating where to start fetching results.
         *
         * @param startAfterId the start-after ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the scannable code to filter by.
         *
         * @param scannableCode the scannable code
         * @return this builder
         */
        public Builder scannableCode(String scannableCode) { this.scannableCode = scannableCode; return this; }

        /**
         * Builds the {@link StorageLocationListRequest}.
         *
         * @return a new {@code StorageLocationListRequest}
         */
        public StorageLocationListRequest build() { return new StorageLocationListRequest(this); }
    }
}
