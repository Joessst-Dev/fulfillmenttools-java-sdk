package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Request payload for listing facility connections with optional filtering and pagination.
 *
 * <p>Use {@link #builder()} to construct instances fluently.
 * All fields are optional.
 *
 * <p>Example:
 * <pre>{@code
 * var request = FacilityConnectionListRequest.builder()
 *     .targetFacilityRef("fac-456")
 *     .size(50)
 *     .startAfterId("last-connection-id")
 *     .build();
 * }</pre>
 */
public final class FacilityConnectionListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String targetFacilityRef;

    private FacilityConnectionListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.targetFacilityRef = builder.targetFacilityRef;
    }

    /** Returns the page size limit. @return the size limit */
    public Integer size() { return size; }

    /** Returns the cursor for pagination. @return the start after ID */
    public String startAfterId() { return startAfterId; }

    /** Returns the target facility reference filter. @return the target facility reference */
    public String targetFacilityRef() { return targetFacilityRef; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.targetFacilityRef = this.targetFacilityRef;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    /** Builder for FacilityConnectionListRequest. */
    public static final class Builder {

        /** The page size limit. */
        private Integer size;

        /** The cursor for pagination. */
        private String startAfterId;

        /** The target facility reference filter. */
        private String targetFacilityRef;

        /** Creates a new Builder. */
        public Builder() {}

        /** Sets the page size limit. @param size the page size. @return this builder */
        public Builder size(Integer size) { this.size = size; return this; }

        /** Sets the cursor for pagination. @param startAfterId the start after ID. @return this builder */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /** Sets the target facility reference filter. @param targetFacilityRef the target facility reference. @return this builder */
        public Builder targetFacilityRef(String targetFacilityRef) { this.targetFacilityRef = targetFacilityRef; return this; }

        /** Builds and returns a new FacilityConnectionListRequest. @return the built request */
        public FacilityConnectionListRequest build() { return new FacilityConnectionListRequest(this); }
    }
}
