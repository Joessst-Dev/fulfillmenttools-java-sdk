package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A built-in (standard) routing strategy rating that ranks candidate facilities using a
 * predefined implementation.
 *
 * <p>Discriminator value: {@code "StandardRating"}
 *
 * @param type           discriminator field, always {@code "StandardRating"} (required)
 * @param active         whether this rating is active (required)
 * @param implementation the rating implementation to use (required)
 * @param maxPenalty     the maximum penalty this rating can contribute (required, minimum 0)
 * @param configuration  optional implementation-specific configuration object
 * @param name           optional display name
 * @param description    optional description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyStandardRating(
        String type,
        Boolean active,
        RatingImplementation implementation,
        Double maxPenalty,
        Object configuration,
        String name,
        String description
) implements RoutingStrategyRating {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyStandardRating}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyStandardRating}.
     */
    public static final class Builder {

        private String type;
        private Boolean active;
        private RatingImplementation implementation;
        private Double maxPenalty;
        private Object configuration;
        private String name;
        private String description;

        private Builder() {}

        /**
         * Sets the discriminator field.
         * @param type the type value, always {@code "StandardRating"}
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets whether this rating is active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the rating implementation to use.
         * @param implementation the rating implementation
         * @return this builder
         */
        public Builder implementation(RatingImplementation implementation) {
            this.implementation = implementation;
            return this;
        }

        /**
         * Sets the maximum penalty this rating can contribute.
         * @param maxPenalty the maximum penalty (minimum 0)
         * @return this builder
         */
        public Builder maxPenalty(Double maxPenalty) {
            this.maxPenalty = maxPenalty;
            return this;
        }

        /**
         * Sets the optional implementation-specific configuration object.
         * @param configuration the configuration object
         * @return this builder
         */
        public Builder configuration(Object configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Sets the optional display name.
         * @param name the display name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the optional description.
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyStandardRating}.
         *
         * @return a new instance.
         */
        public RoutingStrategyStandardRating build() {
            return new RoutingStrategyStandardRating(
                    type, active, implementation, maxPenalty, configuration, name, description);
        }
    }
}
