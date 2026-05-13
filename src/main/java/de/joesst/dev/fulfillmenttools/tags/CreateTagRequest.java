package de.joesst.dev.fulfillmenttools.tags;

import java.util.List;
import java.util.Objects;

/**
 * Request parameters for creating a new tag via {@link TagsClient#create(CreateTagRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * CreateTagRequest request = CreateTagRequest.builder()
 *     .id("priority")
 *     .allowedValues(List.of("LOW", "MEDIUM", "HIGH"))
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class CreateTagRequest {

    private final String id;
    private final List<String> allowedValues;

    private CreateTagRequest(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id must not be null");
        this.allowedValues = Objects.requireNonNull(builder.allowedValues, "allowedValues must not be null");
    }

    /**
     * Returns the identifier for the new tag.
     *
     * @return the tag ID
     */
    public String id() { return id; }

    /**
     * Returns the initial allowed values for the tag.
     *
     * @return the allowed values
     */
    public List<String> allowedValues() { return allowedValues; }

    /**
     * Returns a new {@link Builder} for constructing a {@code CreateTagRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link CreateTagRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String id;
        private List<String> allowedValues;

        /**
         * Sets the identifier for the new tag.
         *
         * @param id the tag ID
         * @return this builder
         */
        public Builder id(String id) { this.id = id; return this; }

        /**
         * Sets the initial allowed values for the tag.
         *
         * @param allowedValues the allowed values
         * @return this builder
         */
        public Builder allowedValues(List<String> allowedValues) { this.allowedValues = allowedValues; return this; }

        /**
         * Builds the {@link CreateTagRequest}. Throws {@link NullPointerException}
         * if any required field is absent.
         *
         * @return a new {@code CreateTagRequest}
         */
        public CreateTagRequest build() { return new CreateTagRequest(this); }
    }
}
