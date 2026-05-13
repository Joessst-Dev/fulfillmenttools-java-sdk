package de.joesst.dev.fulfillmenttools.reservations;

/**
 * Request parameters for listing reservations via
 * {@link ReservationsClient#list(ReservationListRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * ReservationListRequest request = ReservationListRequest.builder()
 *     .size(50)
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class ReservationListRequest {

    private final Integer size;
    private final String after;

    private ReservationListRequest(Builder builder) {
        this.size = builder.size;
        this.after = builder.after;
    }

    /**
     * Returns the maximum number of reservations to return per page.
     *
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination, indicating where to start fetching results.
     *
     * @return the after cursor, or {@code null} if not set
     */
    public String after() { return after; }

    /**
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.after = this.after;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code ReservationListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link ReservationListRequest}.
     */
    public static final class Builder {

        private Integer size;
        private String after;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the maximum number of reservations to return per page.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination, indicating where to start fetching results.
         *
         * @param after the after cursor
         * @return this builder
         */
        public Builder after(String after) { this.after = after; return this; }

        /**
         * Builds the {@link ReservationListRequest}.
         *
         * @return a new {@code ReservationListRequest}
         */
        public ReservationListRequest build() { return new ReservationListRequest(this); }
    }
}
