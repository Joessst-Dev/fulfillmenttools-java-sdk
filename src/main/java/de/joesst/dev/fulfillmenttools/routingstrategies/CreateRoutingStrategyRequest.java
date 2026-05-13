package de.joesst.dev.fulfillmenttools.routingstrategies;

import java.util.Map;
import java.util.Objects;

/**
 * Request object for creating a new routing strategy.
 *
 * <p>Use the {@link Builder} to construct instances:
 * <pre>{@code
 * CreateRoutingStrategyRequest request = CreateRoutingStrategyRequest.builder()
 *     .nameLocalized(Map.of("en_US", "Nearest Facility"))
 *     .build();
 * }</pre>
 */
public final class CreateRoutingStrategyRequest {

    private final Map<String, String> nameLocalized;

    private CreateRoutingStrategyRequest(Builder builder) {
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
    }

    /**
     * Returns the localized name map for the new strategy.
     *
     * @return locale-to-name mapping; never {@code null}
     */
    public Map<String, String> nameLocalized() { return nameLocalized; }

    /**
     * Returns a new {@link Builder}.
     *
     * @return a fresh builder instance
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@link CreateRoutingStrategyRequest}. */
    public static final class Builder {

        private Map<String, String> nameLocalized;

        /**
         * Sets the localized name map.
         *
         * @param nameLocalized locale-to-name mapping; must not be {@code null}
         * @return this builder
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        /**
         * Builds the {@link CreateRoutingStrategyRequest}.
         *
         * @return a new request instance
         * @throws NullPointerException if {@code nameLocalized} was not set
         */
        public CreateRoutingStrategyRequest build() { return new CreateRoutingStrategyRequest(this); }
    }
}
