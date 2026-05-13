package de.joesst.dev.fulfillmenttools.tags;

/**
 * Request parameters for listing tags via {@link TagsClient#list(TagListRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * TagListRequest request = TagListRequest.builder()
 *     .size(50)
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class TagListRequest {

    private final Integer size;
    private final String startAfterId;

    private TagListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    /**
     * Returns the maximum number of tags to return per page.
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
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code TagListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link TagListRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Integer size;
        private String startAfterId;

        /**
         * Sets the maximum number of tags to return per page.
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
         * Builds the {@link TagListRequest}.
         *
         * @return a new {@code TagListRequest}
         */
        public TagListRequest build() { return new TagListRequest(this); }
    }
}
