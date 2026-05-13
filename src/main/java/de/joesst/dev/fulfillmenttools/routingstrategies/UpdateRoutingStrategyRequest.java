package de.joesst.dev.fulfillmenttools.routingstrategies;

import java.util.Map;
import java.util.Objects;

/**
 * Request object for updating an existing routing strategy.
 *
 * <p>Use the {@link Builder} to construct instances:
 * <pre>{@code
 * UpdateRoutingStrategyRequest request = UpdateRoutingStrategyRequest.builder()
 *     .version(2)
 *     .nameLocalized(Map.of("en_US", "Updated Strategy"))
 *     .rootNode(rootNode)
 *     .globalConfiguration(config)    // optional
 *     .build();
 * }</pre>
 */
public final class UpdateRoutingStrategyRequest {

    private final Integer version;
    private final Map<String, String> nameLocalized;
    private final RoutingStrategyNode rootNode;
    private final RoutingStrategyGlobalConfiguration globalConfiguration;

    private UpdateRoutingStrategyRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.rootNode = Objects.requireNonNull(builder.rootNode, "rootNode must not be null");
        this.globalConfiguration = builder.globalConfiguration;
    }

    /**
     * Returns the optimistic-locking version of the strategy being updated.
     *
     * @return version; never {@code null}
     */
    public Integer version() { return version; }

    /**
     * Returns the localized name map.
     *
     * @return locale-to-name mapping; never {@code null}
     */
    public Map<String, String> nameLocalized() { return nameLocalized; }

    /**
     * Returns the root node of the updated routing decision tree.
     *
     * @return root node; never {@code null}
     */
    public RoutingStrategyNode rootNode() { return rootNode; }

    /**
     * Returns the optional global configuration override.
     *
     * @return global configuration, or {@code null} if not set
     */
    public RoutingStrategyGlobalConfiguration globalConfiguration() { return globalConfiguration; }

    /**
     * Returns a new {@link Builder}.
     *
     * @return a fresh builder instance
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@link UpdateRoutingStrategyRequest}. */
    public static final class Builder {

        private Integer version;
        private Map<String, String> nameLocalized;
        private RoutingStrategyNode rootNode;
        private RoutingStrategyGlobalConfiguration globalConfiguration;

        /**
         * Sets the optimistic-locking version.
         *
         * @param version version of the strategy to update; must not be {@code null}
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

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
         * Sets the root node of the routing decision tree.
         *
         * @param rootNode the root node; must not be {@code null}
         * @return this builder
         */
        public Builder rootNode(RoutingStrategyNode rootNode) {
            this.rootNode = rootNode;
            return this;
        }

        /**
         * Sets the optional global configuration.
         *
         * @param globalConfiguration global config override, or {@code null} to leave unchanged
         * @return this builder
         */
        public Builder globalConfiguration(RoutingStrategyGlobalConfiguration globalConfiguration) {
            this.globalConfiguration = globalConfiguration;
            return this;
        }

        /**
         * Builds the {@link UpdateRoutingStrategyRequest}.
         *
         * @return a new request instance
         * @throws NullPointerException if {@code version}, {@code nameLocalized}, or
         *                              {@code rootNode} were not set
         */
        public UpdateRoutingStrategyRequest build() { return new UpdateRoutingStrategyRequest(this); }
    }
}
